//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTCreator;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//
//
//class MockUser {
//    String name;
//    String level;
//
//    MockUser(String name, String level) {
//        this.name = name;
//        this.level = level;
//    }
//}
//
//public class Auth {
//    MockUser user = new MockUser("name","pass");
//    //1.
//    Algorithm algorithm = Algorithm.HMAC256("very_secret");
//
//    //2.
//    JWTGenerator<MockUser> generator = (user, alg) -> {
//        JWTCreator.Builder token = JWT.create()
//                .withClaim("name", user.name)
//                .withClaim("level", user.level);
//        return token.sign(alg);
//    };
//
//    //3.
//    JWTVerifier verifier = JWT.require(algorithm).build();
//
//    //4.
//    JWTProvider provider = JWTProvider(algorithm, generator, verifier);
//}
