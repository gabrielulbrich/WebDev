package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

import controllers.Login;
import views.VeiculoListaView;
import controllers.ConexaoBD;

public class VeiculoListaModel {

	private String descricao;
	private String quantidade;
	private String placa;
	private String cor;
	private String cod_modelo;
	private String cod_fabricante;	
	
	
	//public ResultSet rs;
	
	public static ResultSet resultSetVeiculos;
	public static ResultSet resultSetVeiculosEdit;
	
	
	
	
	
//	public boolean CadastrarVeiculo() {
//		conexao.conexao();
//		try {	
//			PreparedStatement pst = conexao.con.prepareStatement("INSERT INTO VEICULO (descricao, quantidade, imagem, placa, cor, cod_modelo, cod_fabricante) VALUES (?,?,?,?,?,?,?);");
//			pst.setString(1, this.getDescricao());
//			pst.setString(2, this.getQuantidade());
//			pst.setString(3, null);
//			pst.setString(4, this.getPlaca());
//			pst.setString(5, this.getCor());
//			pst.setInt(6, Integer.parseInt(this.getCod_modelo()));
//			pst.setInt(7, Integer.parseInt(this.getCod_fabricante()));
//			
//			//pst.setString(5, this.getSenha_confirmacao());
//			pst.execute();
//			conexao.desconecta();
//			return true;
//			//JOptionPane.showMessageDialog(null, "INSERIDO COM SUCESSO");
//		} catch (SQLException e) {
//		    System.out.println("Erro de SQL: "+e);
//		    e.printStackTrace();
//		}
//		conexao.desconecta();
//		return false;
//	}
	


	public static ArrayList getStudentsListFromDB() {
		Statement stm;
	    ArrayList veiculosList = new ArrayList();  
	    ConexaoBD conexao = new ConexaoBD();
	    conexao.conexao();
	    try {
	    	
			stm = conexao.con.createStatement();
			resultSetVeiculos = stm.executeQuery("SELECT v.cod_veiculo, v.descricao, v.quantidade, v.placa, v.cor, f.fabricante, m.modelo FROM VEICULO AS v, MODELO as m, FABRICANTE as f WHERE v.cod_modelo = m.cod_modelo AND v.cod_fabricante = f.cod_fabricante;");
			
	        while(resultSetVeiculos.next()) {  
	        	VeiculoListaView view = new VeiculoListaView();
//	        	view.setCod_fabricante(resultSetVeiculos.getInt("cod_fabricante"));  
//	        	view.setCod_modelo(resultSetVeiculos.getInt("cod_modelo"));  
	        	view.setCod_veiculo(resultSetVeiculos.getInt("cod_veiculo")); 
	        	view.setModelo(resultSetVeiculos.getString("modelo"));
	        	view.setFabricante(resultSetVeiculos.getString("fabricante"));
	        	view.setPlaca(resultSetVeiculos.getString("placa"));  
	        	view.setCor(resultSetVeiculos.getString("cor"));  
	        	view.setDescricao(resultSetVeiculos.getString("descricao")); 
	        	view.setQuantidade(resultSetVeiculos.getString("quantidade"));
	        	veiculosList.add(view);  
	        }   
	        System.out.println("Total Records Fetched: " + veiculosList.size());
	        conexao.desconecta();
	    } catch(Exception sqlException) {
	        sqlException.printStackTrace();
	    } 
	    return veiculosList;
	}
	
	public static void deletarVeiculo(int cod_veiculo) {
		Statement stm;
	    ArrayList veiculosList = new ArrayList();  
	    ConexaoBD conexao = new ConexaoBD();
	    conexao.conexao();
	    try {	
			stm = conexao.con.createStatement();
			stm.executeQuery("DELETE FROM VEICULO WHERE cod_veiculo='"+cod_veiculo+"';");
	    conexao.desconecta();
	    }catch(Exception sqlException) {
	        sqlException.printStackTrace();
	    } 
	}
	
	
   public static String editStudentRecordInDB(int cod_veiculo) {
	    ConexaoBD conexao = new ConexaoBD();
	    Statement stm = null;
        VeiculoListaView edit = null;
        System.out.println("editStudentRecordInDB() : Veiculo Id: " + cod_veiculo);

        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        conexao.conexao();
        try {
			stm = conexao.con.createStatement();
			resultSetVeiculosEdit = stm.executeQuery("SELECT cod_veiculo, descricao, quantidade, placa, cor, cod_modelo, cod_fabricante FROM VEICULO WHERE cod_veiculo="+cod_veiculo+";");
			
            if(resultSetVeiculosEdit != null) {
            	resultSetVeiculosEdit.next();
                edit = new VeiculoListaView(); 
                //edit.setId(resultSetVeiculosEdit.getInt("student_id"));
                edit.setCod_modelo(resultSetVeiculosEdit.getInt("cod_modelo"));
                edit.setCod_fabricante(resultSetVeiculosEdit.getInt("cod_fabricante"));
                edit.setDescricao(resultSetVeiculosEdit.getString("descricao"));
                edit.setPlaca(resultSetVeiculosEdit.getString("placa"));
                edit.setQuantidade(resultSetVeiculosEdit.getString("quantidade")); 
                edit.setCor(resultSetVeiculosEdit.getString("cor")); 
            }
            sessionMapObj.put("editRecordObj", edit);
            conexao.desconecta();
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/views/editar_veiculo.xhtml?faces-redirect=true";
    }
	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getCod_modelo() {
		return cod_modelo;
	}

	public void setCod_modelo(String cod_modelo) {
		this.cod_modelo = cod_modelo;
	}

	public String getCod_fabricante() {
		return cod_fabricante;
	}

	public void setCod_fabricante(String cod_fabricante) {
		this.cod_fabricante = cod_fabricante;
	}

	


}
