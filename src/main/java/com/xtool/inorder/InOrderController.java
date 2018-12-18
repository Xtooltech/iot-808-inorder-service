package com.xtool.inorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.xtool.enterprise.RespState;
import com.xtool.enterprise.data.DataSearchResult;
import com.xtool.iot808data.inorder.EnableInOrderMaintainer;
import com.xtool.iot808data.inorder.inorderCondition;
import com.xtool.iot808data.inorder.inorderMaintainer;
import com.xtool.iot808data.inorder.inorderModel;

@RestController
@EnableInOrderMaintainer
public class InOrderController {

	@Autowired
	inorderMaintainer dataMaintainer;
	
	/**
	 * 添加在线订单统计信息。
	 * @param data
	 * @return
	 */
	@RequestMapping(path="/inorder/add",method=RequestMethod.POST)
	public RespState<Boolean> add(@RequestBody inorderModel data){
		RespState<Boolean> result=new RespState<>();
		if(data==null || StringUtils.isEmpty(data.oid) || StringUtils.isEmpty(data.sno)||StringUtils.isEmpty(data.uid)) {
			result.setCode(406);
			result.setMsg("Not acceptable");
			result.setData(false);
		}else {
			try {
				boolean opt=dataMaintainer.add(data);
				result.setCode(opt?0:501);
				result.setMsg(opt?"ok":"failed");
				result.setData(opt);
			}catch (Exception e) {
				result.setCode(500);
				result.setMsg(e.getMessage());
				result.setData(false);
			}
		}
		return result;
	}
	/**
	 * 查询在线订单。
	 * @param condition
	 * @return
	 */
	@RequestMapping(value="/inorder/get",method= {RequestMethod.POST})
	public RespState<DataSearchResult<inorderModel>> get(@RequestBody inorderCondition condition){
		if(dataMaintainer==null)return null;
		if(condition==null) {
			condition=new inorderCondition();
			condition.setPageIndex(1);
			condition.setPageSize(1);
		}
		RespState<DataSearchResult<inorderModel>> result=new RespState<DataSearchResult<inorderModel>>();
		try {
			DataSearchResult<inorderModel> data= dataMaintainer.search(condition);
			result.setData(data);
			result.setCode(0);
			result.setMsg("");
		}catch(Exception ex) {
			result.setData(null);
			result.setCode(500);
			result.setMsg(ex.getMessage());
		}
		return result;
	}

	/**
	 * 删除统计信息。
	 * @param oid
	 * @return
	 */
	@RequestMapping(path="/inorder/remove",method= {RequestMethod.POST,RequestMethod.GET})
	public RespState<Boolean> remove(@RequestParam String oid){
		RespState<Boolean> result=new RespState<>();
		if(StringUtils.isEmpty(oid)) {
			result.setCode(406);
			result.setMsg("Not acceptable");
			result.setData(false);
		}else {
			try {
				boolean opt=dataMaintainer.remove(oid);
				result.setCode(opt?0:501);
				result.setMsg(opt?"ok":"failed");
				result.setData(opt);
			}catch (Exception e) {
				result.setCode(500);
				result.setMsg(e.getMessage());
				result.setData(false);
			}
		}
		return result;
	}
}
