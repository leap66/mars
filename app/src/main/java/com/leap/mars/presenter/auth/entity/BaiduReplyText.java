package com.leap.mars.presenter.auth.entity;

import java.util.Arrays;

public class BaiduReplyText {

	private String corpus_no;
	private String err_msg;
	private String err_no;
	private String sn;
	private String result[] = new String[1];

	public BaiduReplyText() {
		super();
	}

	public BaiduReplyText(String corpus_no, String err_msg, String err_no,
			String sn, String[] result) {
		super();
		this.corpus_no = corpus_no;
		this.err_msg = err_msg;
		this.err_no = err_no;
		this.sn = sn;
		this.result = result;
	}

	public String getCorpus_no() {
		return corpus_no;
	}

	public void setCorpus_no(String corpus_no) {
		this.corpus_no = corpus_no;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getErr_no() {
		return err_no;
	}

	public void setErr_no(String err_no) {
		this.err_no = err_no;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String[] getResult() {
		return result;
	}

	public void setResult(String[] result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BaiduReplyText [corpus_no=" + corpus_no + ", err_msg="
				+ err_msg + ", err_no=" + err_no + ", sn=" + sn + ", result="
				+ Arrays.toString(result) + "]";
	}

}
