package com.esteel.web.web;

import com.esteel.common.controller.WebReturnMessage;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 统一的错误处理页面
 * <p>
 * Created by zhangxiuzhi on 2017/6/19.
 */
@ControllerAdvice
public class CommonControllerAdvice {

    public static final SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> ExceptionHandler(HttpServletRequest request, Throwable ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(new WebReturnMessage(false, ex.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<?> handleIoException(HttpServletRequest request, Throwable ex) {
        System.out.println("==================");
        return new ResponseEntity<>(new WebReturnMessage(false, ex.getMessage()), HttpStatus.NOT_FOUND);
//        return "404";
    }

    @ModelAttribute
    public void modelAttribute(Model model, HttpServletRequest request) {

        model.addAttribute("rootPath", request.getContextPath());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            model.addAttribute("isAuthed", false);
        } else {
            String userName = authentication.getName();
            model.addAttribute("isAuthed", true);
            model.addAttribute("userName", userName);
        }


    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Double.class, new DoubleEditor());
        binder.registerCustomEditor(Long.class, new LongEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());

    }


    class DoubleEditor extends PropertiesEditor {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.equals("")) {
                setValue(null);
            } else {
                setValue(Double.parseDouble(text.replaceAll(",", "")));
            }
        }

        @Override
        public String getAsText() {
            if (getValue() == null) {
                return null;
            }
            return nf.format((Double) getValue());
//            return getValue().toString();
        }
    }

    /**
     * 处理long类型
     */
    class LongEditor extends PropertiesEditor {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.equals("")) {
                setValue(null);
            } else {
                setValue(Long.parseLong(text.replaceAll(",", "")));
            }
        }

        @Override
        public String getAsText() {
            if (getValue() == null) {
                return null;
            }
            return getValue().toString();
        }
    }

    /**
     * 处理日期类型
     */
    class DateEditor extends PropertiesEditor {


        @Override
        public void setAsText(String text) throws IllegalArgumentException {

            try {
                if (text == null || text.equals("")) {
                    setValue(null);
                } else if (text.trim().length() == "yyyy-MM-dd".length()) {
                    setValue(sf1.parse(text));
                } else if (text.trim().length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    setValue(sf2.parse(text));
                }
            } catch (ParseException ex) {
                setValue(null);
            }


        }

        @Override
        public String getAsText() {
            Date date = (Date) getValue();
            if (date == null) {
                return null;
            }

            String dateStr1 = sf1.format(date);
            String dateStr2 = sf2.format(date);
            if (dateStr1.equals(dateStr2)) {
                return dateStr1;
            } else {
                return dateStr2;
            }
        }
    }
}
