//��ָ����ʽ��td��Ϊ�ɱ༭��
function resetTds(tableId,td_styleId){
    var numId = $("#"+tableId+" td[styleId='"+td_styleId+"']"); 
    numId.click(function(){ //�ɱ༭td��������¼�
        var tdIns = $(this); //td����
        if ( tdIns.children("input").length>0 ){ return false; } //���Ѿ���������ˣ������κη�Ӧ      

        var inputIns = $("<input type='text'/>"); //��Ҫ����������
        inputIns.width(tdIns.width()); //����input��td���һ�� 
        
        var beforeValue = tdIns.html();
        if(!checkDigst(beforeValue)){//Ԥ��һЩ��ʽ���Ե����ݡ�
        	beforeValue='';
        }
        inputIns.val(beforeValue); //��������Ԫ��td����copy��������ı���input�� 
        tdIns.html(""); //ɾ��ԭ����Ԫ��td���� 
        inputIns.appendTo(tdIns).focus().select(); //����Ҫ����������������dom�ڵ��� 

		/**
        inputIns.mouseout( //���������뿪�¼�
			function(event){ 
				if(checkDigst($(this).val())==false){
					alert('��������ֵ��');
					this.focus().select();
				}
				tdIns.html($(this).val());
			}
        ); 
        **/
        inputIns.blur( //ʧȥ�����¼�
			function(event){ 
				if($(this).val().length==0){
					alert('������ǿ���ֵ��');
					this.focus().select();
					return;
				}
				if(isDigst152($(this).val())==false){
					alert('��������ֵ��');
					this.focus().select();
					return;
				}
				tdIns.html($(this).val());
			}
        );
    }); 
}
/**
	����Ƿ�����ֵ
*/
function checkDigst(value) {
		return /^\d*(\.\d+)?$/.test(value);
};

/**
  �����Ƿ���15.2�͵�����
*/
function isDigst152(str){
   if(str.indexOf(".")==-1){
      if(str.length>13){
         alert(str+"  �������ֲ��ó���"+13+"λ");
         return false;
      }else if(!/^(\d{1,13})$/.test(str)){     
  	     alert(str+" ��������.");
         return false;
      }
   }else if(!/^(\d{1,13})(\.\d{0,2})$/.test(str)){     
      alert(str+"�������ֲ��ó���"+13+"λ��С�����ֲ��ó���"+2+"λ");
      return false;
   }
   return true; 	
}