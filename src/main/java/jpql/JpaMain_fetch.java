package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain_fetch {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);


            Team teamB = new Team();
            teamB.setName("팀A");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            member1.setAge(0);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            member2.setAge(0);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            member3.setAge(0);
            em.persist(member3);


            em.flush();
            em.clear();


//            // join fetch : 조인을 하긴 하는데 fetch(한번에 가져오기)해서 team까지 한번에 가져와라
//            String query = "select m from Member m join fetch m.team";
//            List<Member> member = em.createQuery(query, Member.class).getResultList();
//
//            for (Member member4 : member) {
//                System.out.println("member = " + member4.getUsername() + " , " + member4.getTeam().getName());
//                // 회원1은 영속성 컨텍스트,   팀A는 SQL
//                // 회원2는 영속성 컨텍스트,   팀A는 영속성 컨텍스트(1차 캐시)
//                // 회원3은 영속성 컨텍스트,   팀B는 SQL
//
//                // 이런 상황에서 회원 1000명이면 쿼리가 너무 많이 나감     ->   N + 1
//            }







            // join fetch : 조인을 하긴 하는데 fetch(한번에 가져오기)해서 team까지 한번에 가져와라
//            String query = "select t from Team t join fetch m.team";
//            List<Team> team = em.createQuery(query, Team.class)
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();
//
//            System.out.println("team.size() = " + team.size());
//
//            for (Team team1 : team) {
//                System.out.println("team1 = " + team1.getName() + " , member의 size = " + team1.getMembers().size());
//
//                for (Member member : team1.getMembers()) {
//                    System.out.println("   -> member = " + member);
//                }
//            }





//            String query = "select m from Member m where m.id = :memberId";
//            Member findMember = em.createQuery(query, Member.class)
//                    .setParameter("memberId", member1.getId())
//                    .getSingleResult();
//
//            System.out.println(findMember);
//
//
//            String query1 = "select m from Member m where m.team = :team";
//            List<Member> findMember1 = em.createQuery(query1, Member.class)
//                    .setParameter("team", teamA)
//                    .getResultList();
//
//            System.out.println(findMember1);


//            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "회원1")
//                    .getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }



            // 모든 회원의 나이를 20살로 바꿔보자
            // 결과는 영향을 받은 row의 갯수가 나온다.
            // 벌크연산 수행 전, 후에 flush
            int resultCount = em.createQuery("update Member m set m.age = 40")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            em.clear();

            Member findMember = em.find(Member.class, member2.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());


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
