import React, { useEffect, useState } from "react";
import { getTodo, saveTodo, updateTodo } from "../services/TodoService";
import { useNavigate, useParams } from "react-router-dom";

const TodoComponent = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [completed, setCompleted] = useState(false);
  const navigate = useNavigate();
  const { id } = useParams();

  function saveOrUpdateTodo(e) {
    e.preventDefault();

    const todo = { title, description, completed };

    if (id) {
      updateTodo(id, todo)
        .then(() => {
          navigate("/todos");
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      saveTodo(todo)
        .then(() => {
          navigate("/todos");
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  function pageTitle() {
    return <h2 className="text-center">{id ? "Edit Todo" : "Add Todo"}</h2>;
  }

  useEffect(() => {
    if (id) {
      getTodo(id)
        .then((response) => {
          setTitle(response.data.title);
          setDescription(response.data.description);
          setCompleted(response.data.completed);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, []);

  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <div className="card bg-dark text-white">
            <div className="card-header ">{pageTitle()}</div>
            <div className="card-body">
              <form>
                <div className="mb-3">
                  <label className="form-label">
                    {" "}
                    Title <sup style={{ color: "red" }}>*</sup>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter title"
                    name="title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    {" "}
                    Description<sup style={{ color: "red" }}>*</sup>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter description"
                    name="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Status<sup style={{ color: "red" }}>*</sup>
                  </label>
                  <select
                    className="form-control"
                    value={completed.toString()}
                    onChange={(e) => setCompleted(e.target.value === "true")}
                  >
                    <option value="Select any option" disabled>
                      Select any option
                    </option>
                    <option value="false">No</option>
                    <option value="true">Yes</option>
                  </select>
                </div>

                <div className="d-grid">
                  <button
                    className="btn btn-outline-success"
                    onClick={(e) => saveOrUpdateTodo(e)}
                    disabled={!title || !description}
                  >
                    {id ? "Edit Todo" : "Add Todo"}
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TodoComponent;
