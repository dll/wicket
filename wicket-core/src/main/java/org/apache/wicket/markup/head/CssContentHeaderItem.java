/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.markup.head;

import java.util.Arrays;
import java.util.Collections;

import org.apache.wicket.request.Response;
import org.apache.wicket.core.util.string.CssUtils;
import org.apache.wicket.util.string.Strings;

/**
 * {@link HeaderItem} for internal (embedded in the header) css content.
 * 
 * @author papegaaij
 */
public class CssContentHeaderItem extends CssHeaderItem
{
	private final CharSequence css;
	private final String id;

	/**
	 * Creates a new {@code CSSContentHeaderItem}.
	 * 
	 * @param css
	 *            css content to be rendered.
	 * @param id
	 *            unique id for the &lt;style&gt; element. This can be <code>null</code>, however in
	 *            that case the ajax header contribution can't detect duplicate CSS fragments.
	 */
	public CssContentHeaderItem(CharSequence css, String id, String condition)
	{
		super(condition);
		this.css = css;
		this.id = id;
	}

	/**
	 * @return the css content to be rendered.
	 */
	public CharSequence getCss()
	{
		return css;
	}

	/**
	 * @return unique id for the &lt;style&gt; element.
	 */
	public String getId()
	{
		return id;
	}

	@Override
	public void render(Response response)
	{
		boolean hasCondition = Strings.isEmpty(getCondition()) == false;
		if (hasCondition)
		{
			response.write("<!--[if ");
			response.write(getCondition());
			response.write("]>");
		}

		CssUtils.writeCss(response, getCss(), getId());

		if (hasCondition)
		{
			response.write("<![endif]-->\n");
		}
	}

	@Override
	public Iterable<?> getRenderTokens()
	{
		if (Strings.isEmpty(getId()))
			return Collections.singletonList(getCss());
		return Arrays.asList(getId(), getCss());
	}

	@Override
	public String toString()
	{
		return "CSSHeaderItem(" + getCss() + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CssContentHeaderItem that = (CssContentHeaderItem) o;

		if (css != null ? !css.equals(that.css) : that.css != null) return false;
		return id != null ? id.equals(that.id) : that.id == null;
	}

	@Override
	public int hashCode()
	{
		int result = css != null ? css.hashCode() : 0;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		return result;
	}
}
