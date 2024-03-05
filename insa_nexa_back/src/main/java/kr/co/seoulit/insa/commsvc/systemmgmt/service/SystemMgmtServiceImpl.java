package kr.co.seoulit.insa.commsvc.systemmgmt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import kr.co.seoulit.insa.commsvc.systemmgmt.dto.DetailCodeDTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.entity.DetailCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import kr.co.seoulit.insa.commsvc.systemmgmt.exception.IdNotFoundException;
import kr.co.seoulit.insa.commsvc.systemmgmt.exception.PwMissMatchException;
import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.AuthCodeMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.BoardMapper;
//import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.CodeMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.DetailCodeMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.MenuMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.mapper.ReportMapper;
import kr.co.seoulit.insa.commsvc.systemmgmt.repository.CodeRepository;
import kr.co.seoulit.insa.commsvc.systemmgmt.repository.DetailCodeRepository;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.AdminCodeTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.BoardTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.CodeTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.DetailCodeTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.MenuTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.ReportSalaryTO;
import kr.co.seoulit.insa.commsvc.systemmgmt.to.ReportTO;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.mapper.EmpMapper;
import kr.co.seoulit.insa.empmgmtsvc.empinfomgmt.to.EmpTO;
import kr.co.seoulit.insa.sys.util.BoardFile;

@Service
public class SystemMgmtServiceImpl implements SystemMgmtService {

	@Autowired
	private EmpMapper empMapper;
	@Autowired
	private AuthCodeMapper adminMapper;
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private DetailCodeMapper detailCodeMapper;
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private MenuMapper menuMapper;
//	@Autowired
//	private CodeMapper codeMapper;

	
	@Autowired
	private DetailCodeRepository detailcoderepository;
	
	@Autowired
	private CodeRepository coderepository;
	
	@Override
	public EmpTO findEmp(String empName, String empCode)
			throws IdNotFoundException, PwMissMatchException {
			EmpTO emp = empMapper.selectEmp(empName);

			if (emp == null) {

				throw new IdNotFoundException("사원명이 존재하지 않습니다");

			} else {

				if (emp.getEmpCode().equals(empCode)) {

					return emp;

				} else { // 사원번호가 일치하지 않는경우
					throw new PwMissMatchException("사원번호가 일치하지 않습니다");
				}
			}
	}

	@Override
	@Cacheable(key = "#codetype",value = "codelist")
	public ArrayList<DetailCodeDTO> findDetailCodeList(String codetype) {

		ArrayList<DetailCode> detailCodes = null;

		List<DetailCodeDTO> dto = null;
		if (codetype == null){
			System.out.println(" 🐸🐸🐸🐸🐸1번실행... ");
			detailCodes = (ArrayList<DetailCode>) detailcoderepository.findAll();


			dto = detailCodes.stream().map(kr.co.seoulit.insa.commsvc.systemmgmt.mapstructMapper.DetailCodeMapper.INSTANCE::entityToDetailCodeDTO).collect(Collectors.toList());


		}else{
			System.out.println(" 😾😾😾😾😾2번실행... ");
			System.out.println("========="+ codetype);
			detailCodes = detailcoderepository.findAllBycodeNumber(codetype); //repository에서 entity를 받아옴

			System.out.println("detailCodes = " + detailCodes); //Entity가 들어있음

			dto = detailCodes.stream().map(kr.co.seoulit.insa.commsvc.systemmgmt.mapstructMapper.DetailCodeMapper.INSTANCE::entityToDetailCodeDTO).collect(Collectors.toList());
			//Mapstruct를 통해 entity(detailCodes)를 dto(DetailCodeDTO)로 변환하는 과정


		}
		//detailCodeto = detailCodeMapper.selectDetailCodeList(codetype);
		System.out.println("collect = " + dto); //dto가 들어있음 (화면에 뿌리려면 dto로 뿌려야함)
		System.out.println("🐼🐼🐼🐼🐼");
		return (ArrayList<DetailCodeDTO>)dto;

	}


	@Override
	public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("code1", code1);
		map.put("code2", code2);
		map.put("code3", code3);
		ArrayList<DetailCodeTO> detailCodeto = null;
		detailCodeto = detailCodeMapper.selectDetailCodeListRest(map);
		return detailCodeto;
		
	}

	// ✔️사원상세정보 - [저장]
	@Override
	public void registEmpImg(String empCode, String imgExtend) {

			EmpTO emp = empMapper.selectEmployee(empCode);
			if (emp == null) {
				emp = new EmpTO();
				emp.setEmpCode(empCode);
				emp.setStatus("insert");
			} else {
				emp.setStatus("update");
			}
		// emp가 null이면 insert, 아니라면 update를 실행시키는 구현체이다
			emp.setImgExtend(imgExtend);

			empMapper.updateEmployee(emp);

	}

	@Override
	public ArrayList<CodeTO> findCodeList() {

		ArrayList<CodeTO> codeto = null;
		codeto = (ArrayList<CodeTO>)coderepository.findAll();
		//codeto = codeMapper.selectCode();
		return codeto;
		
	}

	@Override
	public ReportTO viewReport(String empCode) {

		ReportTO to = null;
		to = reportMapper.selectReport(empCode);
		return to;
		
	}

	@Override
	public ReportSalaryTO viewSalaryReport(String empCode, String applyMonth) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("empCode", empCode);
		map.put("applyMonth", applyMonth);
		ReportSalaryTO to = null;
		to = reportMapper.selecSalarytReport(map); 
		return to;
		
	}

	@Override
	public ArrayList<BoardTO> getBoardList() {

		ArrayList<BoardTO> list=null;
		list = boardMapper.selectAllBoardList();
		return list;
		
	}

	@Override
	public void addBoard(BoardTO board) {
		
		if (board.getReply_seq() == 0) {
			boardMapper.insertBoard(board);
		} else {
			boardMapper.insertReplyBoard(board);
		}
		List<BoardFile> files = board.getBoardFiles();
		for (BoardFile file : files) {
			boardMapper.insertBoardFile(file);
		}

	}

	@Override
	public BoardTO getBoard(int board_seq) {

		BoardTO board=null;
		board = boardMapper.selectBoard(board_seq);
		ArrayList<BoardFile> fileList = boardMapper.selectBoardFile(board_seq);
		for (BoardFile file : fileList) {
			board.addBoardFile(file);
		}
		return board;
		
	}

	@Override
	public void changeHit(int board_seq) {

		boardMapper.updateHit(board_seq);
		
	}

	@Override
	public BoardTO getBoard(String sessionId, int board_seq) {

		BoardTO board=null;
		board = boardMapper.selectBoard(board_seq);
		if (!sessionId.equals(board.getName())) { 
			changeHit(board_seq);
		}
		
		ArrayList<BoardFile> fileList = boardMapper.selectBoardFile(board_seq);
		for (BoardFile file : fileList) {
			board.addBoardFile(file);
		}
		return board;
		
	}

	@Override
	public int getRowCount() {

		int dbCount=0;
		dbCount = boardMapper.selectRowCount();
		return dbCount;
		
	}

	@Override
	public ArrayList<BoardTO> getBoardList(int sr, int er) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("start", sr);
		map.put("end", er);
		ArrayList<BoardTO> list=null;
		list = boardMapper.selectBoardList(map);
		return list;
		
	}

	public void removeBoard(int board_seq) {
		
		boardMapper.deleteBoard(board_seq);
		boardMapper.deleteBoardFile(board_seq);
		
	}

	@Override
	public ArrayList<MenuTO> findMenuList() {

		ArrayList<MenuTO> menuList=null;
		menuList = menuMapper.selectMenuList();
		return menuList;
		
	}

	@Override
	public ArrayList<AdminCodeTO> adminCodeList() {
		
		ArrayList<AdminCodeTO> amdList=null;
		amdList = adminMapper.selectAdminCodeList();
		return amdList;
		
	}

	@Override
	public void modifyAuthority(String empCode, String adminCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("empCode", empCode);
		map.put("adminCode", adminCode);
		
			adminMapper.updateAuthority(map);
			
	}

	@Override
	public ArrayList<AdminCodeTO> authadminCodeList(String empno) {

		ArrayList<AdminCodeTO> authadminList=null;
		authadminList = adminMapper.selectAuthAdminCodeList(empno);
		return authadminList;
		
	}
	
}
