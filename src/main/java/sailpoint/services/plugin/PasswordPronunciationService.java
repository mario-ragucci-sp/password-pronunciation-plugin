package sailpoint.services.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import sailpoint.api.SailPointContext;
import sailpoint.object.Attributes;
import sailpoint.object.Configuration;
import sailpoint.object.SAMLConfig;
import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("saml-logout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
@AllowAll
public class PasswordPronunciationService extends BasePluginResource {
	public static final String SQL_SESSION_QUERY = "SELECT SESSION_INDEX FROM SAML_SESSIONS WHERE ACCOUNT=? AND CSRF_TOKEN=?";
	public static final String SQL_REMOVE_DATA	 = "DELETE FROM SAML_SESSIONS WHERE ACCOUNT=? AND CSRF_TOKEN=?";
	public static final Logger _logger = Logger.getLogger(PasswordPronunciationService.class);
    
	@Override
	public String getPluginName() {
		return "password-pronunciation-plugin";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getData")
	public void getData() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "getData"));
		}
		
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getData", null));
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("updateData")
	public String updateData() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "updateData"));
		}
		String result = null;
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "updateData", result));
		}
		return result;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("addData")
	public String addData() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "addData"));
		}
		String result = null;
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "addData", result));
		}
		return result;
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("uploadData")
	public String uploadData() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "uploadData"));
		}
		String result = null;
		
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "uploadData", result));
		}
		return result;	
	}
	
	@DELETE
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteData")
	public String deleteData() {
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
	 * Returns the csrf token of the session
	 * @return
	 */
	private String getCsrfToken() {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "getCsrfToken"));
		}
		String csrfToken = (String) getSession().getAttribute("csrfToken");
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getCsrfToken", csrfToken));
		}
		return csrfToken;
	}

	/**
	 * Returns the IdentityIQ SAML Configuration object
	 * @return the SAMLConfig object
	 * @throws GeneralException
	 */
	private SAMLConfig getSamlConfig() throws GeneralException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s()", "getSamlConfig"));
		}
		SailPointContext context = getContext();
		Configuration samlConfig = context.getObjectByName(Configuration.class, "SAML");
		Attributes<String,Object> attributes = samlConfig.getAttributes();
		
		SAMLConfig config = (SAMLConfig) attributes.get("IdentityNow");
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "getSamlConfig", config));
		}
		return config;
	}
	
	/**
	 * returns a session index from the database
	 * @param principal the name of the logged-in account
	 * @param csrfToken the csrf token associated to the session
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	private String returnSessionIndexFromDb(String principal, String csrfToken) throws GeneralException, SQLException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(principal = %s, csrfToken)", "returnSessionIndexFromDb", principal, csrfToken));
		}
		String result = null;
		Connection connection = getConnection();
		PreparedStatement prepStatement = connection.prepareStatement(SQL_SESSION_QUERY);
		
		prepStatement.setString(1, principal);
		prepStatement.setString(2, csrfToken);
		
		ResultSet rs = prepStatement.executeQuery();
		if(rs.next()) {
			result = rs.getString(1);
		}
		
		rs.close();
		prepStatement.close();
		connection.close();
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "returnSessionIndexFromDb", result));
		}
		return result;
	}
	
	/**
	 * removes a session index from the database
	 * @param principal the name of the logged-in account
	 * @param csrfToken the csrf token associated to the session
	 * @throws GeneralException
	 * @throws SQLException
	 */
	private void removeEntryFromDb(String principal, String csrfToken) throws GeneralException, SQLException {
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("ENTERING method %s(principal = %s, csrfToken)", "removeEntryFromDb", principal, csrfToken));
		}
		Connection connection = getConnection();
		PreparedStatement prepStatement = connection.prepareStatement(SQL_REMOVE_DATA);
		
		prepStatement.setString(1, principal);
		prepStatement.setString(2, csrfToken);
		
		prepStatement.execute();

		prepStatement.close();
		connection.close();
		
		if(_logger.isDebugEnabled()) {
			_logger.debug(String.format("LEAVING method %s (returns: %s)", "removeEntryFromDb", "void"));
		}
	}
}
