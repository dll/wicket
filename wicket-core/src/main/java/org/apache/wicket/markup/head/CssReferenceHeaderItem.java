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

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.bundles.IResourceBundle;
import org.apache.wicket.util.string.Strings;

/**
 * {@link HeaderItem} for style tags that are rendered using a {@link ResourceReference}.
 * 
 * @author papegaaij
 */
public class CssReferenceHeaderItem extends CssHeaderItem implements IReferenceHeaderItem
{
	private final ResourceReference reference;
	private final String media;
	private final PageParameters pageParameters;

	/**
	 * Creates a new {@code CSSReferenceHeaderItem}.
	 * 
	 * @param reference
	 *            resource reference pointing to the CSS resource
	 * @param pageParameters
	 *            the parameters for this CSS resource reference
	 * @param media
	 *            the media type for this CSS ("print", "screen", etc.)
	 * @param condition
	 *            the condition to use for Internet Explorer conditional comments. E.g. "IE 7".
	 */
	public CssReferenceHeaderItem(ResourceReference reference, PageParameters pageParameters,
		String media, String condition)
	{
		super(condition);
		this.reference = reference;
		this.pageParameters = pageParameters;
		this.media = media;
	}

	/**
	 * @return resource reference pointing to the CSS resource
	 * @see IReferenceHeaderItem#getReference()
	 */
	@Override
	public ResourceReference getReference()
	{
		return reference;
	}

	/**
	 * @return the media type for this CSS ("print", "screen", etc.)
	 */
	public String getMedia()
	{
		return media;
	}

	/**
	 * @return the parameters for this CSS resource reference
	 */
	public PageParameters getPageParameters()
	{
		return pageParameters;
	}

	@Override
	public Iterable<? extends HeaderItem> getDependencies()
	{
		return getReference().getDependencies();
	}

	@Override
	public Iterable<? extends HeaderItem> getProvidedResources()
	{
		if (getReference() instanceof IResourceBundle)
			return ((IResourceBundle)getReference()).getProvidedResources();
		return super.getProvidedResources();
	}

	@Override
	public void render(Response response)
	{
		internalRenderCSSReference(response, getUrl(), media, getCondition());
	}

	@Override
	public Iterable<?> getRenderTokens()
	{
		return Arrays.asList("css-" + Strings.stripJSessionId(getUrl()) + "-" + media);
	}

	@Override
	public String toString()
	{
		return "CSSReferenceHeaderItem(" + getReference() + ", " + getPageParameters() + ")";
	}

	private String getUrl()
	{
		IRequestHandler handler = new ResourceReferenceRequestHandler(getReference(),
			getPageParameters());
		return RequestCycle.get().urlFor(handler).toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		CssReferenceHeaderItem that = (CssReferenceHeaderItem) o;

		if (reference != null ? !reference.equals(that.reference) : that.reference != null) return false;
		if (media != null ? !media.equals(that.media) : that.media != null) return false;
		return pageParameters != null ? pageParameters.equals(that.pageParameters) : that.pageParameters == null;
	}

	@Override
	public int hashCode()
	{
		int result = super.hashCode();
		result = 31 * result + (reference != null ? reference.hashCode() : 0);
		result = 31 * result + (media != null ? media.hashCode() : 0);
		result = 31 * result + (pageParameters != null ? pageParameters.hashCode() : 0);
		return result;
	}
}
