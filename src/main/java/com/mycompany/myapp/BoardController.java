package com.mycompany.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/board")
public class BoardController {
    @Autowired
    BoardServicelmpl boardServicelmpl;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String boardlist(Model model){
        List<BoardVO> arraylist = new ArrayList<>();
        arraylist = boardServicelmpl.getBoardList();
        System.out.println(arraylist.get(0).getTitle());
        model.addAttribute("list", boardServicelmpl.getBoardList());
        return "board/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPost(){
        return "board/addpostform";
    }

    @RequestMapping(value = "/addok", method = RequestMethod.POST)
    public String addPostOK(BoardVO vo){
        int i = boardServicelmpl.insertBoard(vo);
        if(i == 0) System.out.println("데이터 추가 실패");
        else System.out.println("데이터 추가 성공!!!");
        return "redirect:list";
    }

    @RequestMapping(value = "/editok", method = RequestMethod.POST)
    public String editPostOK(BoardVO vo){
        int i = boardServicelmpl.updateBoard(vo);
        if(i == 0) System.out.println("데이터 추가 실패");
        else System.out.println("데이터 추가 성공!!!");
        return "redirect:list";
    }

    @RequestMapping(value = "/editpost/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") int id, Model model){
        BoardVO boardVO = boardServicelmpl.getBoard(id);
        model.addAttribute("boardVO", boardVO);
        return "board/editform";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") Integer id, Model model){
        int i = boardServicelmpl.deleteBoard(id);
        if(i==0)
            System.out.println("데이터 삭제 실패");
        else
            System.out.println("데이터 삭제 성공!!!");
        return "redirect:../list";
    }
}
