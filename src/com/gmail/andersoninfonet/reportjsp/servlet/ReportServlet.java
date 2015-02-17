package com.gmail.andersoninfonet.reportjsp.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.andersoninfonet.reportjsp.dao.ReportDAO;
import com.gmail.andersoninfonet.reportjsp.modelo.Pessoa;
import com.gmail.andersoninfonet.reportjsp.modelo.User;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebServlet("/report")
public class ReportServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/* EXEMPLO PARA POPULAR UM OBJETO SEM CONEXÃO COM BANCO
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	public List<Pessoa> getPessoas() {
		
		Pessoa pe = new Pessoa();
		pe.setNome("Eduardo");
		pe.setApelido("Dudu");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 3, 19);
		pe.setDataNascimento(cal.getTime());
		pessoas.add(pe);
		
		pe = new Pessoa();
		pe.setNome("Nubia");
		pe.setApelido("Binha");
		
		cal = Calendar.getInstance();
		cal.set(2010, 5, 29);
		pe.setDataNascimento(cal.getTime());
		pessoas.add(pe);
		
		return pessoas;
	}*/
	
	
	private ReportDAO dao = new ReportDAO();
	private List<User> lista;
	//populando a lista através da conexao com banco
	public List<User> getLista(){
		lista = dao.getUsuarios();
		return lista;
	}
	
	private ServletContext context;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		context = req.getSession().getServletContext();
		//arquivo .jasper compilado para conexao com banco
		File jasper = new File(context.getRealPath("/Banco_Green_.jasper"));
		//EXEMPLO PARA POPULAR UM RELATORIO SEM CONEXÃO COM BANCO
		//File jasper = new File(context.getRealPath("/Leaf_Green.jasper"));
		
		try {
			//exemplo que pega lista populada pela conexao com banco
			byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, new JRBeanCollectionDataSource(this.getLista()));
			//EXEMPLO PARA POPULAR UM RELATORIO SEM CONEXÃO COM BANCO
			//byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, new JRBeanCollectionDataSource(this.getPessoas()));
			res.setContentType("application/pdf");
			res.setContentLength(bytes.length);
			ServletOutputStream out = res.getOutputStream();
			
			out.write(bytes, 0 , bytes.length);
			out.flush();
			out.close();
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	}

}
