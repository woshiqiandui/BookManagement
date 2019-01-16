package action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * function:�����������
 * @author ��Ǩ��
 * 
 */
public class onlineListener implements HttpSessionListener {
     
	//��ҳ���ϳ���������Ϊnull������������Ϳ��Իָ������� ��
	private int onlineNumber = 0;

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent hse)  { 
   
    	//�����������
    	onlineNumber++;
    	//���session����
    	HttpSession session = hse.getSession();
    	//��ȡServlet������
    	ServletContext  context = session.getServletContext();
    	//����servlet����������
    	context.setAttribute("onlineNumber", onlineNumber);
    	
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent hse)  { 
    	//����ʹ��������С��0
     	if(onlineNumber>0){
    	//������������
    	onlineNumber--;
    	//���session����
    	HttpSession session = hse.getSession();
    	//��ȡServlet������
    	ServletContext  context = session.getServletContext();
    	//����servlet����������
    	context.setAttribute("onlineNumber", onlineNumber);
     	}
    	
    }
	
}
