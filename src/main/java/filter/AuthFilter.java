package filter;

import java.io.IOException;
import java.util.List;

import exception.ExceptionHandler;
import exception.VemNoX1Exception;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import service.vemnox1.LoginService;

@Provider
public class AuthFilter implements ContainerRequestFilter{

	private static final String BASE_URL_RESTRITA = "restrito";
	private static final String SESSION_ID_KEY = "sessionId";
	private static final String LOGIN_KEY = "login";
	
	private LoginService loginService = new LoginService();
	

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		UriInfo url = requestContext.getUriInfo();
		if(url.getPath().contains(BASE_URL_RESTRITA)) {
			List<String> keysSessionId = requestContext.getHeaders().get(SESSION_ID_KEY);
			List<String> keysLogin = requestContext.getHeaders().get(LOGIN_KEY);
			if(keysSessionId != null && keysSessionId.size() == 1 
					&& keysLogin != null && keysLogin.size() == 1) {
				String sessionId = keysSessionId.get(0);
				String login = keysLogin.get(0);
				validarApiKey(sessionId, login, requestContext);
			}else {
//				ResponseBuilder responseBuilder = Response.serverError();
//				Response response = responseBuilder.status(Status.UNAUTHORIZED).build();
//				requestContext.abortWith(response);
				montarResponseUnauthorized(requestContext);
			}
		}
	}

	private void validarApiKey(String idSessao, String login, ContainerRequestContext requestContext) {
		if(!loginService.chaveValida(idSessao, login)) {
			montarResponseUnauthorized(requestContext);
		}
	}

	private void montarResponseUnauthorized(ContainerRequestContext requestContext) {
		VemNoX1Exception exception = new VemNoX1Exception("Usuário sem acesso");
		String json = ExceptionHandler.converterExceptionParaJson(exception);

		Response response = Response.status(Status.FORBIDDEN)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.entity(json)
				.build();
		
		requestContext.abortWith(response);
	}
}
