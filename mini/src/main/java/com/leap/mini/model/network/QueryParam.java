package com.leap.mini.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  : ylwei
 * @time    : 2017/9/5
 * @description :
 */
public class QueryParam implements Serializable {
  private int start;
  private int limit;
  private List<FilterParam> filters = new ArrayList<>();
  private List<SortParam> sorters = new ArrayList<>();

  public QueryParam() {
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

  public FilterParam findFilter(String property) {
    for (FilterParam param : getFilters()) {
      if (property.equals(param.getProperty())) {
        return param;
      }
    }
    return null;
  }

  public void removeFilter(String property) {
    for (FilterParam param : getFilters()) {
      if (property.equals(param.getProperty())) {
        getFilters().remove(param);
        return;
      }
    }
  }
}
