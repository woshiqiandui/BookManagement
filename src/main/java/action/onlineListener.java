package action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * function:监测在线人数
 * @author 陈迁对
 * 
 */
public class onlineListener implements HttpSessionListener {
     
	//当页面上出现在线数为null，重启浏览器就可以恢复数字了 。
	private int onlineNumber = 0;

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent hse)  { 
   
    	//添加在线人数
    	onlineNumber++;
    	//获得session变量
    	HttpSession session = hse.getSession();
    	//获取Servlet上下文
    	ServletContext  context = session.getServletContext();
    	//设置servlet上下文属性
    	context.setAttribute("onlineNumber", onlineNumber);
    	
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent hse)  { 
    	//不能使在线人数小于0
     	if(onlineNumber>0){
    	//减少在线人数
    	onlineNumber--;
    	//获得session变量
    	HttpSession session = hse.getSession();
    	//获取Servlet上下文
    	ServletContext  context = session.getServletContext();
    	//设置servlet上下文属性
    	context.setAttribute("onlineNumber", onlineNumber);
     	}
    	
    }
	
}
