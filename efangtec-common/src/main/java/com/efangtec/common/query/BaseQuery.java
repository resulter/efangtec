package com.efangtec.common.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class BaseQuery implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Integer pageSize = 10;//defaut 10

	private Integer currentPage = 1;

	private int startRow = 0;

	private Integer page;//当前页码

	private Integer limit;//页面大小

	private Map<String,Object> paramMap =new HashMap<>();

	public Integer getPageSize()
	{
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		if (pageSize == null || pageSize <= 0)
		{
			return;
		}
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage)
	{
		if (currentPage == null || currentPage <= 0)
		{
			return;
		}
		this.currentPage = currentPage;
	}

	public Integer getStartRow()
	{
		if (limit == null || limit < 0 || page == null || page < 0)
		{
			return 0;
		}
		startRow = (page - 1) * limit;
		return startRow;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public Map<String, Object> build()
	{
		Map<String, Object> params = new HashMap<String, Object>(5);
		params.put("pageSize", this.getLimit());
		params.put("currentPage", this.getCurrentPage());
		params.put("startRow", this.getStartRow());
		params.putAll(paramMap);
		return params;
	}

	protected void getParameter(Class<?> clazz, Map<String, Object> map, Object obj) throws Exception
	{
		if (clazz.getSimpleName().equals("Object"))
		{
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		if ((fields != null) && (fields.length > 0))
		{
			for (int i = 0; i < fields.length; i++)
			{
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				if (Modifier.isStatic(fields[i].getModifiers()))
				{
					continue;
				}
				Object value = fields[i].get(obj);
				map.put(name, value);
			}
		}
		//Class<?> superClzz = clazz.getSuperclass();
		//getParameter(superClzz, map, obj);
	}

}
