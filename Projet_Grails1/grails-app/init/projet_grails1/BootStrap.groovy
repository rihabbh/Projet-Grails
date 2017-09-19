package projet_grails1

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        def modRole = new Role(authority: 'ROLE_MOD').save(flush: true, failOnError: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)

        def userInstance = new User(username: 'NINOU', password: 'COCO1234').save(flush: true, failOnError: true) ;
        //def poisInstance = new Pois(nom : 'monptipois', description: 'le gogo ninou est un tout pti user').save(flush: true, failOnError: true);

        (1..3).each {
        int groupIndex ->
        def poisGroupInstance = new GroupPois(nomGroup: 'group '+groupIndex).save(flush: true, failOnError: true)
            (1..5).each {
                int poisIndex ->
               poisGroupInstance.addToPois(
                        new Pois(nom:"Poi"+poisIndex,description: 'ptiResume'+poisIndex).save(flush: true, failOnError: true)

                )
            }
   }
        def modInstance= new User(username: 'NI', password: 'COCO1234').save(flush: true,failOnError:true)
        def adminInstance= new User(username: 'NOU', password: 'COCO1234').save(flush: true,failOnError:true)


        def user1 = new UserRole(user : userInstance, role : userRole).save(flush: true,failOnError:true)
        def user2 = new UserRole(user : modInstance, role : modRole).save(flush: true,failOnError:true)
        def user3 = new UserRole(user : adminInstance, role :adminRole).save(flush: true,failOnError:true)

        //  UserRole creat userInstance, userRole, true
       // UserRole create modInstance, modRole, true
       // UserRole create adminInstance, adminRole, true
    }
    def destroy = {

    }
}
