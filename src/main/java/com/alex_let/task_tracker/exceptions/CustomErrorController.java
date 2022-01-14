package com.alex_let.task_tracker.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class CustomErrorController implements ErrorController //пробрасывает error на клиента
{
    private static final String PATH = "/error";

    //спринг подтянет атрибуты ошибки?
    private static ErrorAttributes errorAttributes;

    @RequestMapping(PATH)//путь по которому возвращаются все ошибки
        public ResponseEntity<ErrorDto/*наш объект*/> error(WebRequest webRequest)
    {
        //маппируем атрибуты ошибки в мапу webRequest:ErrorAttributeOptions
        Map<String, Object> attributes = errorAttributes
            .getErrorAttributes(
            webRequest,
            ErrorAttributeOptions.of(
            ErrorAttributeOptions.Include.EXCEPTION,
            ErrorAttributeOptions.Include.MESSAGE));

        //собираем dtoшку из ошибки
        return ResponseEntity
            .status((Integer) /*наша мапа*/attributes.get("status")) //берем http status
            .body(ErrorDto //наш объект
                .builder()
                .error((String) attributes.get("error")) //responseStatus указанный в аннотации к эксепшену
                .errorDescription((String) attributes.get("message")) //сообщение об ошибке переданное исключению
                .build());

    }

}
