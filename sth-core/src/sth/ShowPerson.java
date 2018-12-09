package sth;

public class ShowPerson implements ShowUser {
    public String showAdmin(Administrative a){
        String myEol = System.getProperty("line.separator"); 
        return "FUNCIONÁRIO|" + a.toString() + myEol;
    }
    public String showStudent(Student st){
        String type = (st.isRepresentative()? "DELEGADO" : "ALUNO");
        String s = type + "|" + st.toString();
        return s + st.getSubjects();
    }
    public String showProfessor(Professor p){
        return "DOCENTE|" + p.toString() + p.orderSubjects();
    }
}