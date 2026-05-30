package egovframework.lab.aop.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.lab.aop.service.EmpVO;

@Repository("annotationEmpDAO")
public class AnnotationEmpDAO {

    static List<EmpVO> list;

    static {
        list = new ArrayList<EmpVO>();
        EmpVO empVO;
        for (int i = 1; i <= 100; i++) {
            empVO = new EmpVO();
            empVO.setEmpNo(i);
            empVO.setEmpName("EmpName " + i);
            empVO.setJob("SALESMAN");
            list.add(empVO);
        }
    }

    public void insertEmp(EmpVO empVO) throws Exception {
        list.add(empVO);
        Collections.sort(list);
    }

    public void updateEmp(EmpVO empVO) throws Exception {
        int index = Collections.binarySearch(list, empVO);
        // 해당 데이터가 없는 경우 여기서는 ArrayIndexOutOfBoundsException 발생할 것임
        EmpVO orgEmpVO = list.get(index);

        orgEmpVO.setEmpName(empVO.getEmpName());
        orgEmpVO.setJob(empVO.getJob());
    }

    public void deleteEmp(EmpVO empVO) throws Exception {
        list.remove(Collections.binarySearch(list, empVO));
        Collections.sort(list);
    }

    public EmpVO selectEmp(EmpVO empVO) throws Exception {
        int index = Collections.binarySearch(list, empVO);

        // list search 결과 해당값을 찾을 수 없으면 음수값
        // (-(insertion point) - 1) 이 되돌려짐
        return index < 0 ? null : list.get(index);
    }

    public List<EmpVO> selectEmpList() throws Exception {
        return list;
    }

}
