package createtemplatewithoutrobot;

import TemplateContabil.Model.Template;
import fileManager.FileManager;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CreateTemplateWithoutRobot {

    public static void main(String[] args) {
        //Pega empresa
        try {
            Integer enterprise = Integer.valueOf(JOptionPane.showInputDialog("Qual o número da empresa no Único?"));
            Integer account = Integer.valueOf(JOptionPane.showInputDialog("Qual o número da conta / banco no Único?"));
            Integer hD = Integer.valueOf(JOptionPane.showInputDialog("Qual o histórico padrão de débito no Único?"));
            Integer hC = Integer.valueOf(JOptionPane.showInputDialog("Qual o histórico padrão de crédito no Único?"));
            String cfgStr = "E" + enterprise + "_C" + account + "_HD" + hD + "_HC" + hC;

            // Cria o arquivo INI com essas configurações
            String defaultTemplateConfig = FileManager.getText(FileManager.getFile("defaultTemplateConfig.ini"));
            defaultTemplateConfig = defaultTemplateConfig.replaceAll(":enterprise", enterprise.toString());
            defaultTemplateConfig = defaultTemplateConfig.replaceAll(":account", account.toString());
            defaultTemplateConfig = defaultTemplateConfig.replaceAll(":hd", hD.toString());
            defaultTemplateConfig = defaultTemplateConfig.replaceAll(":hc", hC.toString());
            defaultTemplateConfig = defaultTemplateConfig.replaceAll(":contabilizacao", cfgStr);
            
            FileManager.save(Template.templateParaCopiar.getParentFile(),cfgStr + ".ini", defaultTemplateConfig);            
            
            
            //Cria arquivo
            String nomeArquivoSalvo = cfgStr + ".xlsm";
            String desktop = System.getProperty("user.home") + "/Desktop";

            File arquivoSalvo = new File(desktop + "\\" + nomeArquivoSalvo);

            Template template = new Template(
                    0,
                    0,
                    arquivoSalvo,
                    cfgStr,
                    new ArrayList<>());

            if (template.criarTemplateXlsm()) {
                JOptionPane.showMessageDialog(null, "Template do banco " + account + " salvo em " + arquivoSalvo.getPath());
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu algum erro ao tentar salvar o arquivo na área de trabalho!", "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O valor informado deve ser um número!", "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        System.exit(0);
    }

}
