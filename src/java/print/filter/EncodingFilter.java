/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.filter;

/**
 *
 * @author apopovkin
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author apopovkin
 */
public class EncodingFilter implements Filter {

	private String encoding = "cp1251";

	public void doFilter(ServletRequest request,

	ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String encodingParam = filterConfig.getInitParameter("encoding");
		if (encodingParam != null) {
			encoding = encodingParam;
		}
	}

	public void destroy() {
		// nothing todo
	}

}