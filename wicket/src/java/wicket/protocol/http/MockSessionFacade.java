/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar
 * 2006) eelco12 $ $Revision: 5004 $ $Date: 2006-03-17 20:47:08 -0800 (Fri, 17
 * Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.protocol.http;

import wicket.Request;
import wicket.Session;
import wicket.SessionFacade;
import wicket.session.ISessionStore;

/**
 * Test session facade.
 * 
 * @author Eelco Hillenius
 */
public class MockSessionFacade extends SessionFacade
{
	private Session session;

	/**
	 * @see wicket.SessionFacade#bind(wicket.Request, wicket.Session)
	 */
	public void bind(Request request, Session newSession)
	{
		this.session = newSession;
	}

	/**
	 * @see wicket.SessionFacade#getSessionId(wicket.Request)
	 */
	public String getSessionId(Request request)
	{
		return "sessionid";
	}

	/**
	 * @see wicket.SessionFacade#lookup(wicket.Request)
	 */
	protected Session lookup(Request request)
	{
		return session;
	}

	/**
	 * @see wicket.SessionFacade#newSessionStore(java.lang.String)
	 */
	protected ISessionStore newSessionStore(String sessionId)
	{
		return new HttpSessionStore();
	}
}
