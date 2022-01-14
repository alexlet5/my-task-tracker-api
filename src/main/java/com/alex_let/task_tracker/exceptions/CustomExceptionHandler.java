package com.alex_let.task_tracker.exceptions;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2 //страшно вырубай
@ControllerAdvice //дает всем контроллерам свои методы????
//класс перехватывает все эксепшены
public class CustomExceptionHandler extends ResponseEntityExceptionHandler//
{

    @ExceptionHandler(Exception.class)//все экспешены
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) throws Exception
    {
        log.error("Exception during execution of application", ex);

        //генерирует объект ошибки, добавляет в хедеры информацию об ошибке
        return handleException(ex, request);
        //возвращает непонятно че
    }
}
