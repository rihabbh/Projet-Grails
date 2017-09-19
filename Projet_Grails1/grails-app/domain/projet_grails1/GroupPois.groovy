package projet_grails1

class GroupPois {
    String nomGroup;
    static hasMany = [pois:Pois, medias:Media]

    static constraints = {
        pois nullable:  true
        medias nullable: true
        nomGroup blank: false

    }

}
