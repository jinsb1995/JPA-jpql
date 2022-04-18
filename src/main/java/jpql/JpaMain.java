package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("관리자");
//            member.setAge(10);
//            member.setMemberType(MemberType.ADMIN);
////            member.setTeam(team);
//
//            member.setTeam(team);


            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);


            em.flush();
            em.clear();


//            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();

//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();


            System.out.println("==============================================");

//            List<Team> result = em.createQuery("select m.team from Member m", Team.class).getResultList();
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
//            em.createQuery("select o.address from Order o", Address.class).getResultList();


            // 프로젝션 - 1. Query 타입으로 조회
//            List resultList = em.createQuery("select distinct m.username, m.age from Member m").getResultList();
//
//            Object o = resultList.get(0);
//
//            Object[] result = (Object[]) o;
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);

            
            // 프로젝션 - 2. Object[] 타입으로 조회
//            List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member m").getResultList();
//
//            Object[] result = resultList.get(0);
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);


            // 프로젝션 - 3. new 명령어로 조회
//            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
//
//            MemberDTO memberDTO = resultList.get(0);
//            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
//            System.out.println("memberDTO.getAge () = " + memberDTO.getAge());


            // 페이징 API
//            List<Member> resultList = em.createQuery("select m from Member m order by m.username asc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }


//            String query = "select m.username, 'HELLO', true from Member m " +
//                    "where m.memberType = :userType";
//            List<Object[]> result = em.createQuery(query).setParameter("userType", MemberType.ADMIN).getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects[0] = " + objects[0]);
//                System.out.println("objects[1] = " + objects[1]);
//                System.out.println("objects[2] = " + objects[2]);
//            }


//            String query =
//                    "select nullif(m.username, '관리자') from Member m ";
//            List<String> resultList = em.createQuery(query, String.class).getResultList();
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }



//            String query = "select function('group_concat', m.username) From Member m";
            String query = "select group_concat(m.username) From Member m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();

    }
}
