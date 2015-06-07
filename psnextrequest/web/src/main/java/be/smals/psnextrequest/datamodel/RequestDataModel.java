package be.smals.psnextrequest.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import be.smals.psnextrequest.controller.RequestConsultController;

public class RequestDataModel extends ListDataModel<RequestConsultController> implements SelectableDataModel<RequestConsultController>{

	public RequestDataModel() {  
    }  
  
    public RequestDataModel(List<RequestConsultController> data) {  
        super(data);  
    }  
      
    @Override  
    public RequestConsultController getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        @SuppressWarnings("unchecked")
		List<RequestConsultController> requests = (List<RequestConsultController>) getWrappedData();  
          
        for(RequestConsultController request : requests) {  
            if(request.getId().equals(rowKey))  
                return request;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(RequestConsultController request) {  
        return request.getId();  
    } 
}
