
public class FamigliaTest{

    public static void main(String[] args) {
        Famiglia f=new Famiglia("xmR_naz.xml");
        System.out.println(f.isVivo("Paolo", "De Checchi"));
        System.out.println(f.isNato("1938"));
        System.out.println(f.isMorto("1970"));
        System.out.println(f.consorte("Teresa", "temp"));
        System.out.println(f.avi("Antonio", "Giora"));
        System.out.println(f.prolifica());
        System.out.println("Stranieri: "+f.getStraniere());
        System.out.println("Nazionalita: "+f.getNazionalita());
        System.out.println("Morti: "+f.morti());
        System.out.println("Persone: " + f.persone());
    }
}