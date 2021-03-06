package dao;

import br.pucpr.prog4.loja.models.dao.DaoException;
import br.pucpr.prog4.loja.models.dao.IDaoManager;
import br.pucpr.prog4.loja.models.dao.JdbcPessoaDAO;
import br.pucpr.prog4.loja.models.dao.PessoaDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcDaoManager implements IDaoManager 
{
    private Connection conexão;
    private JdbcPessoaDAO pessoaDAO;
    
    public JdbcDaoManager()
    {
        
    }
    
    
    @Override
    public void iniciar() throws DaoException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url;
            url = "jdbc:mysql://localhost:3306/loja";
            conexão = DriverManager.getConnection(url, "root", "root");
            conexão.setAutoCommit(false);
            pessoaDAO = new JdbcPessoaDAO(conexão);
//            clienteD.setConexão(conexão);
            
        }
        catch( Exception ex )
        {
            throw new DaoException("Ocorreu um erro ao conectar ao banco de dados:" + 
                    ex.getMessage());
        }
    }

    @Override
    public void encerrar() 
    {
        try {
            if(!conexão.isClosed())
                conexão.close();
        } catch (SQLException ex) {
            
        }
    }

    @Override
    public void confirmarTransação(){
        try {
            conexão.commit();
        } catch (SQLException ex) {
            throw new DaoException("Ocorreu um erro ao confirmar a transação");
        }
    }

    @Override
    public void abortarTransação() {
        try {
            conexão.rollback();
        } catch (SQLException ex) {
            throw new DaoException("Ocorreu um erro ao abordar a transição");
        }
    }

    @Override
    public PessoaDAO getPessoaDAO() {
        return pessoaDAO;
    }
    
}
