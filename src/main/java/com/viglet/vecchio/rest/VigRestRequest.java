package com.viglet.vecchio.rest;
 
import java.util.regex.Pattern;
import java.util.regex.Matcher;
 
import javax.servlet.ServletException;

public class VigRestRequest {

    // Accommodate two requests, one for all resources, another for a specific resource
    private Pattern regExAllPattern = Pattern.compile("/resource");
    private Pattern regExIdPattern = Pattern.compile("/resource/([0-9]*)");
 
    private Integer id;
 
    public VigRestRequest(String pathInfo) throws ServletException {
      // regex parse pathInfo
      Matcher matcher;
 
      // Check for ID case first, since the All pattern would also match
      matcher = regExIdPattern.matcher(pathInfo);
      if (matcher.find()) {
        id = Integer.parseInt(matcher.group(1));
        return;
      }
 
      matcher = regExAllPattern.matcher(pathInfo);
      if (matcher.find()) return;
 
      throw new ServletException("Invalid URI");
    }
 
    public Integer getId() {
      return id;
    }
 
    public void setId(Integer id) {
      this.id = id;
    }
  }