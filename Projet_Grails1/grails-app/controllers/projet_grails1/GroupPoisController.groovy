package projet_grails1

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class GroupPoisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond GroupPois.list(params), model:[groupPoisCount: GroupPois.count()]
    }

    def show(GroupPois groupPois) {
        respond groupPois
    }

    def create() {
        respond new GroupPois(params)
    }

    @Transactional
    def save(GroupPois groupPois) {
        if (groupPois == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (groupPois.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupPois.errors, view:'create'
            return
        }

        groupPois.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'groupPois.label', default: 'GroupPois'), groupPois.id])
                redirect groupPois
            }
            '*' { respond groupPois, [status: CREATED] }
        }
    }

    def edit(GroupPois groupPois) {
        respond groupPois
    }

    @Transactional
    def update(GroupPois groupPois) {
        if (groupPois == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (groupPois.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond groupPois.errors, view:'edit'
            return
        }

        groupPois.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'groupPois.label', default: 'GroupPois'), groupPois.id])
                redirect groupPois
            }
            '*'{ respond groupPois, [status: OK] }
        }
    }

    @Transactional
    def delete(GroupPois groupPois) {

        if (groupPois == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        groupPois.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'groupPois.label', default: 'GroupPois'), groupPois.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'groupPois.label', default: 'GroupPois'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
