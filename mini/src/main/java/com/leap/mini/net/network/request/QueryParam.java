package com.leap.mini.net.network.request;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求参数实体类
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class QueryParam {
  private int start;
  private int limit = 10; // 每页默认10条
  private List<FilterParam> filters = new ArrayList<FilterParam>();
  private List<SortParam> sorters = new ArrayList<SortParam>();

  public QueryParam() {
  }

  public QueryParam(int limit) {
    this.limit = limit;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public List<FilterParam> getFilters() {
    return filters;
  }

  public void setFilters(List<FilterParam> filters) {
    this.filters = filters;
  }

  public List<SortParam> getSorters() {
    return sorters;
  }

  public void setSorters(List<SortParam> sorters) {
    this.sorters = sorters;
  }

  public void nextPage() {
    this.start = this.start + this.getLimit();
  }

  public void resetPage() {
    this.start = 0;
  }

  public FilterParam findFilter(String property) {
    for (FilterParam param : getFilters()) {
      if (property.equals(param.getProperty())) {
        return param;
      }
    }
    return null;
  }

  public void remove(String property) {
    for (FilterParam param : getFilters()) {
      if (property.equals(param.getProperty())) {
        getFilters().remove(param);
        return;
      }
    }
  }
}
