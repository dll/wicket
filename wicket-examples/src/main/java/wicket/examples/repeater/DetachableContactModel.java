/*
 * $Id: DetachableContactModel.java 5832 2006-05-24 05:36:28 +0000 (Wed, 24 May
 * 2006) ivaynberg $ $Revision$ $Date: 2006-05-24 05:36:28 +0000 (Wed,
 * 24 May 2006) $
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.examples.repeater;

import wicket.markup.repeater.IItemReuseStrategy;
import wicket.model.LoadableDetachableModel;

/**
 * detachable model for an instance of contact
 * 
 * @author igor
 * 
 */
public class DetachableContactModel extends LoadableDetachableModel<Contact>
{
	private long id;

	protected ContactsDatabase getContactsDB()
	{
		return DatabaseLocator.getDatabase();
	}

	/**
	 * @param c
	 */
	public DetachableContactModel(final Contact c)
	{
		super(c);
		if (c == null)
		{
			throw new IllegalArgumentException();

		}
		id = c.getId();

	}

	/**
	 * @param id
	 */
	public DetachableContactModel(final long id)
	{
		if (id == 0)
		{
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return new Long(id).hashCode();
	}

	/**
	 * used for dataview with ReuseIfModelsEqualStrategy item reuse strategy
	 * 
	 * @see wicket.markup.repeater.PageableRefreshingView#setItemReuseStrategy(IItemReuseStrategy)
	 * @see wicket.markup.repeater.ReuseIfModelsEqualStrategy
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		else if (obj == null)
		{
			return false;
		}
		else if (obj instanceof DetachableContactModel)
		{
			DetachableContactModel other = (DetachableContactModel)obj;
			return other.id == this.id;
		}
		return false;
	}

	/**
	 * @see wicket.model.LoadableDetachableModel#load()
	 */
	@Override
	protected Contact load()
	{
		// loads contact from the database
		return getContactsDB().get(id);
	}
}
