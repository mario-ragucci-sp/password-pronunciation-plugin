package sailpoint.services.plugin.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.sun.jersey.multipart.FormDataParam;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("password-pronunciation")
@AllowAll
public class PasswordPronunciationService extends BasePluginResource {
	public static String SQL_INSERT_QUERY		= "INSERT INTO PASSWORD_PRONUNCIATION_MAPPING(DATA_KEY,DATA_VALUE) VALUES(?,?)";
	public static String SQL_UPDATE_QUERY		= "UPDATE PASSWORD_PRONUNCIATION_MAPPING SET DATA_KEY=?, DATA_VALUE=? WHERE ID=?";
	public static String SQL_DELETE_QUERY		= "DELETE FROM PASSWORD_PRONUNCIATION_MAPPING WHERE ID=?";
	public static String SQL_SELECT_ALL_QUERY	= "SELECT ID,DATA_KEY,DATA_VALUE FROM PASSWORD_PRONUNCIATION_MAPPING";
	public static String SQL_SELECT_QUERY		= "SELECT ID,DATA_KEY,DATA_VALUE FROM PASSWORD_PRONUNCIATION_MAPPING WHERE ID=?";
	
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
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mappingtable")
	public Response getAllDataSingleObject() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "getAllDataSingleObject"));
		}
		
		Map<String, Object> result = null;
		try {
			result = readEntriesSingleObject();
		} catch (GeneralException e) {
			_logger.error(e.getMessage());
		}
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getAllDataSingleObject", result));
		}
		
		return Response.ok().entity(result).build();
	}
	
	/**
	 * @param id
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("data/{id}")
	public Response getData(@PathParam("id") int id) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s)", "getData", id));
		}
		
		Response response = null;
		Map<String, Object> result = null;
		
		try {
			result = readEntry(id);
		} catch (GeneralException e) {
			_logger.error(e.getMessage());
		}
		
		if(result != null) {
			response = Response.ok().entity(result).build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getData", response));
		}
		return response;
	}

	/**
	 * @param id
	 * @param key
	 * @param value
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("data")
	public Response updateData(@FormParam("id") int id, @FormParam("key") String key, @FormParam("value") String value) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s, key = %s, value = %s)", "updateData", id, key, value));
		}
		int result = 0;
		Response response = null;
		try {
			result = updateEntry(id, key, value);
		} catch (GeneralException e) {
			_logger.error(e.getMessage());
		}
		
		if(result >= 1) {
			response = Response.ok().build();
		} else {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "updateData", response));
		}
		return response;
	}
	
	/**
	 * @param id
	 * @param key
	 * @param value
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("data")
	public Response createData(@FormParam("key") String key, @FormParam("value") String value) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(key = %s, value = %s)", "createData", key, value));
		}
		Response response = null;
		Boolean result = false;
		try {
			result = createEntry(key, value);
		} catch (GeneralException e) {
			_logger.error(e.getMessage());
		}
		
		if(result) {
			response = Response.ok().entity(result).build();
		} else {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "createData", result));
		}
		return response;
	}
	
	/**
	 * @param file
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("uploadData")
	public Response uploadData(@FormDataParam("file") InputStream uploadedInputStream) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(file = %s)", "uploadData", uploadedInputStream));
		}
		Response response = null;
		Map<String, Object> result = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(uploadedInputStream));
		String line = null;
		try {
			while((line = br.readLine()) != null) {
				String[] parts = line.split(";");
				result.put(parts[0], parts[1]);
			}
		} catch (IOException e) {
			_logger.error(e.getMessage());
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					_logger.error(e.getMessage());
				}
			}
			if(uploadedInputStream != null) {
				try {
					uploadedInputStream.close();
				} catch (IOException e) {
					_logger.error(e.getMessage());
				}
			}
		}
		
		if(result != null) {
			response = Response.ok().entity(result).build();
		} else {
			response = Response.status(Status.NOT_ACCEPTABLE).build();
		}
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "uploadData", response));
		}
		return response;	
	}
		
	/**
	 * @param id
	 * @return
	 */
	@DELETE
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
	private Boolean createEntry(String key, String value) throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(key = %s, value = %s)", "createEntry", key, value));
		}
		int success = 0;
		Boolean result = false;
		Connection connection = getConnection();
		PreparedStatement prepStatement = null;
		try {
			prepStatement = connection.prepareStatement(SQL_INSERT_QUERY);
			prepStatement.setString(1, key);
			prepStatement.setString(2, value);
			
			success = prepStatement.executeUpdate();
			if(success == 1) {
				result = true;
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
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "createEntry", result));
		}
		
		return result;
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
			while(rs.next()) {
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
	
	/**
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	private Map<String, Object> readEntriesSingleObject() throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "readEntriesSingleObject"));
		}
		Map<String, Object> result = new HashMap<>();
		
		Connection connection = getConnection();
		PreparedStatement prepStatement = null;
		try {
			prepStatement = connection.prepareStatement(SQL_SELECT_ALL_QUERY);
			ResultSet rs = prepStatement.executeQuery();
			while(rs.next()) {
				result.put(rs.getString(2), rs.getString(3));
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
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "readEntriesSingleObject", result));
		}
		return result;
	}
	
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
			
			int resultInt = prepStatement.executeUpdate();
			if(resultInt == 1) {
				success = true;
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
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "deleteEntry", success));
		}
		
		return success;
	}
}
