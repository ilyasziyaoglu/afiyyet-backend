package com.smartmenu.common.webfilters;

import com.smartmenu.common.utils.JwtUtil;
import com.smartmenu.common.user.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-24
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private UserService userService;
	private JwtUtil jwtUtil;

	public JwtRequestFilter(
			final UserService userService,
			final JwtUtil jwtUtil
	) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");

		String token = null;
		String username = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && !authorizationHeader.contains("null")) {
			token = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(token);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userService.loadUserByUsername(username);

			if (jwtUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request,response);
	}
}
