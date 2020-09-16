//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.smartmenu.common.basemodel.request.pager;


import com.smartmenu.common.basemodel.response.BaseResponse;

public class PageResource<T> extends BaseResponse {
    private static final long serialVersionUID = 1L;
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private T data;

    public int getDraw() {
        return this.draw;
    }

    public long getRecordsTotal() {
        return this.recordsTotal;
    }

    public long getRecordsFiltered() {
        return this.recordsFiltered;
    }

    public T getData() {
        return this.data;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "PageResource(draw=" + this.getDraw() + ", recordsTotal=" + this.getRecordsTotal() + ", recordsFiltered=" + this.getRecordsFiltered() + ", data=" + this.getData() + ")";
    }

    public PageResource(int draw, long recordsTotal, long recordsFiltered, T data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public PageResource() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResource)) {
            return false;
        } else {
            PageResource<?> other = (PageResource)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                return super.equals(o);
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageResource;
    }

    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}
