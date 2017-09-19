package projet_grails1

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LocalisationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Localisation.list(params), model:[localisationCount: Localisation.count()]
    }

    def show(Localisation localisation) {
        respond localisation
    }

    def create() {
        respond new Localisation(params)
    }

    @Transactional
    def save(Localisation localisation) {
        if (localisation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (localisation.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond localisation.errors, view:'create'
            return
        }

        localisation.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'localisation.label', default: 'Localisation'), localisation.id])
                redirect localisation
            }
            '*' { respond localisation, [status: CREATED] }
        }
    }

    def edit(Localisation localisation) {
        respond localisation
    }

    @Transactional
    def update(Localisation localisation) {
        if (localisation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (localisation.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond localisation.errors, view:'edit'
            return
        }

        localisation.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'localisation.label', default: 'Localisation'), localisation.id])
                redirect localisation
            }
            '*'{ respond localisation, [status: OK] }
        }
    }

    @Transactional
    def delete(Localisation localisation) {

        if (localisation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        localisation.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'localisation.label', default: 'Localisation'), localisation.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'localisation.label', default: 'Localisation'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
