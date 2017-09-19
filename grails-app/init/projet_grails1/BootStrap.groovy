package projet_grails1

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: 'Administrateur').save(flush: true, failOnError: true)
        def modRole = new Role(authority: 'Moderateur').save(flush: true, failOnError: true)
        def userRole = new Role(authority: 'Utilisateur').save(flush: true, failOnError: true)

        def userInstance = new User(username: 'NINOU', password: 'COCO1234').save(flush: true, failOnError: true) ;

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
        def modInstance= new User(username: 'Amira', password: '1234').save(flush: true,failOnError:true)
         def adminInstance= new User(username: 'Rihab', password: '1234').save(flush: true,failOnError:true)

         def user1 = new UserRole(user: userInstance,role : userRole).save(flush: true, failOnError: true)
         def user2 = new UserRole(user : modInstance, role : modRole).save(flush: true, failOnError: true)
        def user3 = new UserRole(user : adminInstance, role : adminRole).save(flush: true, failOnError: true)


        //  UserRole create modInstance, modRole, true
       // UserRole create adminInstance, adminRole, true
    }
    def destroy = {

    }
}
