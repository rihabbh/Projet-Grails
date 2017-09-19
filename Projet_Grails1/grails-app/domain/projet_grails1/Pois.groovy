package projet_grails1

class Pois {

    String nom;
    String description;

    static hasOne = [loc: Localisation ]
    static hasMany = [images: Media  ]
    static belongsTo    =[ group:GroupPois]
    static constraints = {

        nom blank : false
        description blank : false
        loc nullable: true
        images nullable :true
        group nullable :true

    }
}
