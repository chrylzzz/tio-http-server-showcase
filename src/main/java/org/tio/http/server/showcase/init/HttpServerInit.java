package org.tio.http.server.showcase.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ssl.SslUtils;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.handler.DefaultHttpRequestHandler;
import org.tio.http.server.mvc.Routes;
import org.tio.http.server.showcase.HttpServerShowcaseStarter;
import org.tio.server.ServerGroupContext;
import org.tio.utils.SystemTimer;


/**
 * @author tanyaowu
 * 2017年7月19日 下午4:59:04
 */
public class HttpServerInit {
	private static Logger log = LoggerFactory.getLogger(HttpServerInit.class);

	public static HttpConfig httpConfig;

	public static HttpRequestHandler requestHandler;

	public static HttpServerStarter httpServerStarter;

	public static ServerGroupContext serverGroupContext;

	public static void init() throws Exception {
		long start = SystemTimer.currTime;

		int port = P.getInt("http.port");//启动端口
		String pageRoot = P.getString("http.page");//html/css/js等的根目录，支持classpath:，也支持绝对路径
		httpConfig = new HttpConfig(port, null, null, null);
		httpConfig.setPageRoot(pageRoot);
		httpConfig.setMaxLiveTimeOfStaticRes(P.getInt("http.maxLiveTimeOfStaticRes"));
		httpConfig.setPage404(P.getString("http.404"));
		httpConfig.setPage500(P.getString("http.500"));

		String[] scanPackages = new String[] { HttpServerShowcaseStarter.class.getPackage().getName() };//tio mvc需要扫描的根目录包，会递归子目录
		Routes routes = new Routes(scanPackages);
		requestHandler = new DefaultHttpRequestHandler(httpConfig, routes);

		httpServerStarter = new HttpServerStarter(httpConfig, requestHandler);
		serverGroupContext = httpServerStarter.getServerGroupContext();
		httpServerStarter.start();   //启动http服务器

		String protocol = SslUtils.isSsl(serverGroupContext) ? "https" : "http";

		long end = SystemTimer.currTime;
		long iv = end - start;
		if (log.isInfoEnabled()) {
			log.info("\r\nTio Http Server启动完毕,耗时:{}ms\r\n访问地址:{}://127.0.0.1:{}", iv, protocol, port);
		} else {
			System.out.println("\r\nTio Http Server启动完毕,耗时:" + iv + "ms,\r\n访问地址:" + protocol + "://127.0.0.1:" + port);
		}
	}

}
