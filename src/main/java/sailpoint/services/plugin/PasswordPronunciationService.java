package sailpoint.services.plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("saml-logout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
@AllowAll
public class PasswordPronunciationService extends BasePluginResource {
	public static String SQL_INSERT_QUERY		= "INSERT INTO PASSWORD_PRONUNCIATION_MAPPING(KEY,VALUE) VALUES(?,?)";
	public static String SQL_UPDATE_QUERY		= "UPDATE PASSWORD_PRONUNCIATION_MAPPING SET KEY=?, VALUE=? WHERE ID=?";
	public static String SQL_DELETE_QUERY		= "DELETE FROM PASSWORD_PRONUNCIATION_MAPPING WHERE ID=?";
	public static String SQL_SELECT_ALL_QUERY	= "SELECT ID,KEY,VALUE FROM PASSWORD_PRONUNCIATION_MAPPING";
	public static String SQL_SELECT_QUERY		= "SELECT ID,KEY,VALUE FROM PASSWORD_PRONUNCIATION_MAPPING WHERE ID=?";
	
	public static final Logger _logger = Logger.getLogger(PasswordPronunciationService.class);
    
	@Override
	public String getPluginName() {
		return "password-pronunciation-plugin";
	}
	
	/**
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("data")
	public Response getAllData() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "getAllData"));
		}
		
		List<Map<String, Object>> result = null;
		try {
			result = readEntries();
		} catch (GeneralException e) {
			_logger.error(e.getMessage());
		}
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getData", result));
		}
		
		return Response.ok().entity(result).build();
	}
	
	/**
	 * @param id
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("data/{id}")
	public void getData(@PathParam("id") int id) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s)", "getData", id));
		}
		
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getData", null));
		}
	}

	/**
	 * @param id
	 * @param key
	 * @param value
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("data")
	public String updateData(@FormParam("id") int id, @FormParam("key") String key, @FormParam("value") String value) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s, key = %s, value = %s)", "updateData", id, key, value));
		}
		String result = null;
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "updateData", result));
		}
		return result;
	}
	
	/**
	 * @param id
	 * @param key
	 * @param value
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("data")
	public String createData(@FormParam("id") int id, @FormParam("key") String key, @FormParam("value") String value) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s, key = %s, value = %s)", "createData", id, key, value));
		}
		String result = null;
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "createData", result));
		}
		return result;
	}
	
	/**
	 * @param file
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("uploadData")
	public String uploadData(@FormParam("file") File file) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "uploadData"));
		}
		String result = null;
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "uploadData", result));
		}
		return result;	
	}
	
	/**
	 * @param id
	 * @return
	 */
	@DELETE
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("data/{id}")
	public Response deleteData(@PathParam("id") int id) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "deleteData"));
		}
		Response response = null;
		Boolean result = false;
		try {
			result = deleteEntry(id);
		} catch (GeneralException e) {
			_logger.error(e.getMessage());
		}
		
		if(result) {
			response = Response.ok().build();
		}else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "deleteData", response));
		}
		
		return response;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private Boolean createEntry(String key, String value) throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(key = %s, value = %s)", "createEntry", key, value));
		}
		boolean success = false; 
		Connection connection = getConnection();
		PreparedStatement prepStatement = null;
		try {
			prepStatement = connection.prepareStatement(SQL_INSERT_QUERY);
			prepStatement.setString(1, key);
			prepStatement.setString(2, value);
			
			success = prepStatement.execute();
		} catch (SQLException e) {
			_logger.error(e.getMessage());
		} finally {
			if(prepStatement != null) {
				try {
					prepStatement.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
		}

		

		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "createEntry", success));
		}
		
		return success;
	}
	
	/**
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	private List<Map<String, Object>> readEntries() throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "readEntries"));
		}
		List<Map<String, Object>> result = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement prepStatement = null;
		try {
			prepStatement = connection.prepareStatement(SQL_SELECT_ALL_QUERY);
			ResultSet rs = prepStatement.executeQuery();
			if(rs.next()) {
				Map<String, Object> resultObj = new HashMap<>();
				resultObj.put("id", rs.getInt(1));
				resultObj.put("key", rs.getString(2));
				resultObj.put("value", rs.getString(3));
				
				result.add(resultObj);
			}
		} catch (SQLException e) {
			_logger.error(e.getMessage());
		} finally {
			if(prepStatement != null) {
				try {
					prepStatement.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
		}
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "readEntries", result));
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	private Map<String, Object> readEntry(int id) throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s)", "readEntry", id));
		}
		Map<String, Object> result = new HashMap<>();
		
		Connection connection = getConnection();
		PreparedStatement prepStatement = null;
		try {
			prepStatement = connection.prepareStatement(SQL_SELECT_QUERY);
			prepStatement.setInt(1, id);
			
			ResultSet rs = prepStatement.executeQuery();
			if(rs.next()) {
				result.put("id", rs.getInt(1));
				result.put("key", rs.getString(2));
				result.put("value", rs.getString(3));
			}
		} catch (SQLException e) {
			_logger.error(e.getMessage());
		} finally {
			if(prepStatement != null) {
				try {
					prepStatement.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
		}

		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "readEntry", result));
		}
		return result;
	}
	
	/**
	 * @param id
	 * @param key
	 * @param value
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private int updateEntry(int id, String key, String value) throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s, key = %s, value = %s)", "updateEntry", id, key, value));
		}
		int updatedEntries = 0;
		Connection connection = getConnection();
		PreparedStatement prepStatement = null;
		try {
			prepStatement = connection.prepareStatement(SQL_UPDATE_QUERY);
			prepStatement.setString(1, key);
			prepStatement.setString(2, value);
			prepStatement.setInt(3, id);
			
			updatedEntries = prepStatement.executeUpdate();
		} catch (SQLException e) {
			_logger.error(e.getMessage());
		} finally {
			if(prepStatement != null) {
				try {
					prepStatement.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
		}

		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "updateEntry", updatedEntries));
		}
		
		return updatedEntries;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	private Boolean deleteEntry(int id) throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s)", "deleteEntry", id));
		}
		Boolean success = false; 
		Connection connection = getConnection();
		PreparedStatement prepStatement = null;
		try {
			prepStatement = connection.prepareStatement(SQL_DELETE_QUERY);
			prepStatement.setInt(1, id);
			
			success = prepStatement.execute();
		} catch (SQLException e) {
			_logger.error(e.getMessage());
		} finally {
			if(prepStatement != null) {
				try {
					prepStatement.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					_logger.error(e.getMessage());
				}
			}
		}
				
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "deleteEntry", success));
		}
		
		return success;
	}
}
