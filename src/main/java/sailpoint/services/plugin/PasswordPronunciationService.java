package sailpoint.services.plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

import org.apache.log4j.Logger;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("saml-logout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
@AllowAll
public class PasswordPronunciationService extends BasePluginResource {
	public static final String SQL_INSERT_QUERY		= "INSERT INTO PASSWORD_PRONUNCIATION_MAPPING(KEY,VALUE) VALUES(?,?)";
	public static final String SQL_UPDATE_QUERY		= "UPDATE PASSWORD_PRONUNCIATION_MAPPING SET KEY=?, VALUE=? WHERE ID=?";
	public static final String SQL_DELETE_QUERY		= "DELETE FROM PASSWORD_PRONUNCIATION_MAPPING WHERE ID=?";
	public static final String SQL_SELECT_ALL_QUERY	= "SELECT ID,KEY,VALUE FROM PASSWORD_PRONUNCIATION_MAPPING";
	public static final String SQL_SELECT_QUERY		= "SELECT ID,KEY,VALUE FROM PASSWORD_PRONUNCIATION_MAPPING WHERE ID=?";
	
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
	public void getAllData() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "getAllData"));
		}
		
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getData", null));
		}
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("data")
	public String addData(@FormParam("id") int id, @FormParam("key") String key, @FormParam("value") String value) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s, key = %s, value = %s)", "addData", id, key, value));
		}
		String result = null;
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "addData", result));
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
	public String deleteData(@PathParam("id") int id) {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "deleteData"));
		}
		String result = null;
		
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "deleteData", result));
		}
		return result;
	}
	
	
	/**
	 * @param id
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private Boolean deleteEntryInDb(int id) throws GeneralException, SQLException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s)", "deleteEntryInDb", id));
		}
		Boolean success = false; 
		Connection connection = getConnection();
		PreparedStatement prepStatement = connection.prepareStatement(SQL_DELETE_QUERY);
		
		prepStatement.setInt(1, id);
		
		success = prepStatement.execute();

		prepStatement.close();
		connection.close();
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "deleteEntryInDb", success));
		}
		
		return success;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private Boolean createEntryInDb(String key, String value) throws GeneralException, SQLException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(key = %s, value = %s)", "createEntryInDb", key, value));
		}
		boolean success = false; 
		Connection connection = getConnection();
		PreparedStatement prepStatement = connection.prepareStatement(SQL_INSERT_QUERY);

		prepStatement.setString(1, key);
		prepStatement.setString(2, value);
		
		success = prepStatement.execute();

		prepStatement.close();
		connection.close();
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "createEntryInDb", success));
		}
		
		return success;
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
	private int updateEntryInDb(int id, String key, String value) throws GeneralException, SQLException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(id = %s, key = %s, value = %s)", "updateEntryInDb", id, key, value));
		}
		int updatedEntries = 0;
		Connection connection = getConnection();
		PreparedStatement prepStatement = connection.prepareStatement(SQL_UPDATE_QUERY);

		prepStatement.setString(1, key);
		prepStatement.setString(2, value);
		prepStatement.setInt(3, id);
		
		updatedEntries = prepStatement.executeUpdate();

		prepStatement.close();
		connection.close();
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "updateEntryInDb", updatedEntries));
		}
		
		return updatedEntries;
	}
}
