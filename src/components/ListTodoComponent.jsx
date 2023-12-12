import React, { useEffect, useState } from "react";
import {
  completeTodo,
  deleteTodo,
  getAllTodos,
  inCompleteTodo,
} from "../services/TodoService";
import { isAdminUser } from "../services/AuthService";
import { useNavigate } from "react-router-dom";

const ListTodoComponent = () => {
  const [todos, setTodos] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    listTodos();
  }, []);

  function listTodos() {
    getAllTodos()
      .then((response) => {
        setTodos(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function addNewTodo() {
    navigate("/add-todo");
  }

  function updateTodo(id) {
    navigate(`/update-todo/${id}`);
  }

  function handleTodoAction(action, id) {
    action(id)
      .then(() => {
        listTodos();
      })
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <div className="container">
      <h2 className="text-center my-4">TODOS</h2>
      {isAdminUser() && (
        <button
          className="btn btn-outline-dark w-100 mb-3"
          onClick={addNewTodo}
        >
          Add Todo
        </button>
      )}
      <div>
        <table className="table table-responsive table-bordered table-striped">
          <thead className="table-dark">
            <tr>
              <th>Title</th>
              <th>Description</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {todos.map((todo) => (
              <tr key={todo.id}>
                <td>{todo.title}</td>
                <td>{todo.description}</td>
                <td>{todo.completed ? "YES" : "NO"}</td>
                <td>
                  {isAdminUser() && (
                    <button
                      className="btn btn-outline-warning btn-sm"
                      onClick={() => updateTodo(todo.id)}
                    >
                      Edit
                    </button>
                  )}
                  {isAdminUser() && (
                    <button
                      className="btn btn-outline-danger btn-sm ms-2"
                      onClick={() => handleTodoAction(deleteTodo, todo.id)}
                    >
                      Delete
                    </button>
                  )}
                  <button
                    className={`btn btn-outline-${
                      todo.completed ? "secondary" : "success"
                    } btn-sm ms-2`}
                    onClick={() =>
                      handleTodoAction(
                        todo.completed ? inCompleteTodo : completeTodo,
                        todo.id
                      )
                    }
                  >
                    {todo.completed ? "In Complete" : "Complete"}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListTodoComponent;
