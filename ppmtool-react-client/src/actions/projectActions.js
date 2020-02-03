import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async dispatch => {
  try {
    //const res = await axios.post("http://localhost:8080/api/project", project);
    const res = await axios.post("/api/project", project); // base URL is proxy defined in package.json
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getProjects = () => async dispatch => {
  const res = await axios.get("http://localhost:8080/api/project/all");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data
  });
};

export const getProject = (id, history) => async dispatch => {
  try {
    const res = await axios.get(`/api/project/${id}`, id); //base URL is proxy defined in package.json
    dispatch({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch (errors) {
    history.push("/dashboard");
  }
};

export const deleteProject = id => async dispatch => {
  var msg =
    "Are you sure? This will delete the project " +
    id +
    " and all the data related to it";
  if (window.confirm(msg)) {
    await axios.delete(`/api/project/${id}`, id); // base URL is proxy defined in package.json
    dispatch({
      type: DELETE_PROJECT,
      payload: id
    });
  }
};
