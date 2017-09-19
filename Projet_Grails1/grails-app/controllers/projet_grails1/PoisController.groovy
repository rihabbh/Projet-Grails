package projet_grails1

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PoisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Pois.list(params), model:[poisCount: Pois.count()]
    }

    def show(Pois pois) {
        respond pois
    }

    def create() {
        respond new Pois(params)
    }

    @Transactional
    def save(Pois pois) {
        if (pois == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pois.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pois.errors, view:'create'
            return
        }

        pois.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pois.label', default: 'Pois'), pois.id])
                redirect pois
            }
            '*' { respond pois, [status: CREATED] }
        }
    }

    def edit(Pois pois) {
        respond pois
    }

    @Transactional
    def update(Pois pois) {
        if (pois == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pois.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pois.errors, view:'edit'
            return
        }

        pois.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pois.label', default: 'Pois'), pois.id])
                redirect pois
            }
            '*'{ respond pois, [status: OK] }
        }
    }

    @Transactional
    def delete(Pois pois) {

        if (pois == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pois.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pois.label', default: 'Pois'), pois.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pois.label', default: 'Pois'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
