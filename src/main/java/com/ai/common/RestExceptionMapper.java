package com.ai.common;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Provider
public class RestExceptionMapper implements ExceptionMapper<Exception> {

	private static Logger logger = LogManager.getLogger(RestExceptionMapper.class.getName());
	
	@Override
	public Response toResponse(Exception exception) {

	    logger.info("Catch Exception = " + exception);
		if(exception  instanceof YamiException){
			return Response.status(Status.OK).entity(exception).build();
		}

		YamiException error = new YamiException(YamiConstant.ERRORCODE_ER1006,ErrorCodeEnum.ER1006.getMsg());
	    return Response.status(Status.OK).entity(error).build();
	}

}
