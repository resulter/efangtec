package com.efangtec.common.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页查询结果
 * @param <T>
 */
public class QueryResult<T> implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer totalPage;//总页数
	private Integer totalRecord;//总条数
	private Collection<T> resultList = new ArrayList<T>();//当前页数据集合
	private BaseQuery query;

	public QueryResult(){

	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalPage()
	{
		return this.totalPage;
	}

	public Integer getTotalRecord()
	{
		return this.totalRecord;
	}

	public void setTotalRecord(Integer totalRecord)
	{
		if (query == null)
		{
			this.totalRecord  =totalRecord;
			return;
		}
		int pageSize = query.getPageSize();
		int mod = totalRecord % pageSize;
		int page = totalRecord / pageSize;
		this.totalPage = (mod == 0 ? page : page + 1);
		this.totalRecord  =totalRecord;
		/*if (totalRecord == null)
		{
			return;
		}
		if (query == null)
		{
			return;
		}
		int pageSize = query.getPageSize();
		int mod = totalRecord % pageSize;
		int page = totalRecord / pageSize;
		this.totalPage = (mod == 0 ? page : page + 1);
		this.totalRecord = totalRecord;*/
	}


	public Collection<T> getResultList()
	{
		return resultList;
	}

	public void setResultList(Collection<T> resultList)
	{
		this.resultList = resultList;
	}

	public BaseQuery getQuery()
	{
		return query;
	}

	public void setQuery(BaseQuery query)
	{
		this.query = query;
	}

	/*public Integer getNextPage()
	{
		Integer nextPage = query.getCurrentPage() + 1;
		return nextPage > totalPage ? -1 : nextPage;
	}*/

	//***************************utils*****************************//
	/*public Map<String, Object> propertyMap()
	{
		Map<String, Object> pm = new HashMap<String, Object>(5);
		pm.put("totalRecord", this.totalRecord);
		pm.put("totalPage", this.totalPage);
		pm.put("resultList", this.resultList);
		if (query != null)
		{
			pm.putAll(query.build());
		}
		return pm;
	}*/
}
