package org.wicketstuff.urlfragment.example.asyncpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.urlfragment.BookmarkableAjaxButton;
import org.wicketstuff.urlfragment.BookmarkableAjaxLink;

public class ContentPanel extends Panel
{

	private final StringValue linkParam;

	public ContentPanel(String id, IRequestParameters requestParameters)
	{
		super(id);
		linkParam = requestParameters.getParameterValue("linkParam");
	}

	public ContentPanel(String id, PageParameters requestParameters)
	{
		super(id);
		linkParam = requestParameters.get("linkParam");
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		final IModel<String> sortingModel = Model.of(linkParam == null ? "" : linkParam.toString());

		final Label sortingLabel = new Label("fragment", sortingModel);
		sortingLabel.setOutputMarkupId(true);
		add(sortingLabel);

		add(new BookmarkableAjaxLink<Void>("addParam")
		{

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				urlFragment().addParameter("linkParam", "add", "|");
				target.add(sortingLabel);
			}

		});

		add(new BookmarkableAjaxLink<Void>("setParam")
		{

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				target.add(sortingLabel);
				urlFragment().setParameter("linkParam", "set");
			}

		});

		add(new BookmarkableAjaxLink<Void>("removeParam")
		{

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				target.add(sortingLabel);
				urlFragment().removeParameter("linkParam");
			}

		});

		add(new BookmarkableAjaxLink<Void>("setFragment")
		{

			@Override
			public void onBookmarkableClick(AjaxRequestTarget target)
			{
				target.add(sortingLabel);
				urlFragment().set("calendar");
			}

		});

		final Model<String> formModel = Model.of("");
		final Form<String> form = new StatelessForm<String>("form");
		form.setOutputMarkupId(true);
		form.add(new TextField<String>("formParam", formModel));
		form.add(new BookmarkableAjaxButton("submit")
		{

			@Override
			protected void onBookmarkableSubmit(AjaxRequestTarget target, Form<?> form)
			{
				urlFragment().setParameter("formParam", formModel.getObject());
				target.add(form);
			}

			@Override
			protected void onBookmarkableError(AjaxRequestTarget target, Form<?> form)
			{
			}
		});
		add(form);
	}
}
