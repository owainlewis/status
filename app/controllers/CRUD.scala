package controllers

trait CRUD {

  def index

  def show(id: Long)

  def create

  def update(id: Long)

  def delete(id: Long)
}