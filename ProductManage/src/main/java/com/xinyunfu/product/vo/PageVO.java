package com.xinyunfu.product.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PageVO<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int current_page;   //当前页
	private int size;           //每页数量
	private int total_page;     //总页数
	private int total_sum;      //总条数
	@JsonIgnore
	private transient List<T> instanceList;//Gson不序列化transient字段
	private List<T> currentPageData;    //当前页数据
	@JsonIgnore
	private transient Optional<List<T>> op;
	
	public PageVO(List<T> instanceList,int size) {
		this.size = size;
		setInstanceList(instanceList);
	}
	
	public int getCurrent_page() {

		return current_page;
	}
	public void setCurrent_page(int current_page) {
		this.current_page = current_page<1?1:current_page>this.total_page?this.total_page:current_page;
		setCurrentPageData(currentPageData());
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotal_page() {
		return total_page;
	}
	public int getTotal_sum() {
		return total_sum;
	}
	public List<T> getInstanceList() {
		return instanceList;
	}
	public void setInstanceList(List<T> instanceList) {
		this.op= Optional.ofNullable(instanceList);
		this.instanceList = op.orElse(new ArrayList<T>());
		this.total_sum = this.instanceList.size();
		this.total_page =(int) Math.ceil(1.0*this.total_sum/this.size);
	}
	public void setCurrentPageData(List<T> currentPageData) {
		this.currentPageData = currentPageData;
	}
	public List<T> getCurrentPageData(){
		return this.currentPageData;
	}
	public List<T> currentPageData(){
		if(this.size==0 || this.total_page == 1){
			return this.instanceList;
		}
		List<T> currentPageData = new ArrayList<>();
		instanceList.stream().skip((this.current_page-1)*this.size).limit(this.size).forEach(e->currentPageData.add(e));
		return currentPageData;
	}




}
