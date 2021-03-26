import React, {useEffect, useState} from 'react';

import styled from 'styled-components';

import {Button, Table, Dropdown, Modal, Alert} from 'react-bootstrap';

import {MdArrowDropDown, MdArrowDropUp} from 'react-icons/md'

import axios from 'axios';

const Styles = styled.div`

  header {
    background-color: darkolivegreen;
    display: flex;
    justify-content: center;
    
    h1 {
    color: white;
    padding: 20px 0;
    }
  }
  
  #active {
    text-decoration: underline;
  }
  
  #alert-div {
    display: flex;
    justify-content: center;
  }
  
  #table-wrapper {
    display: flex;
    justify-content: center;
    margin-top: 50px;
    
    .table {
      width: 650px;
      position: relative;
      
      #add-button {
        position: absolute;
        width: 100px;
        top: -45px;
        right: 0px;
      }
      
      div.td-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        
        button.sort-button {
          border: none;
          background-color: transparent;
        }
      }
      
      #change-column {
        width: 140px;
      }
      #delete-column {
        width: 50px;
      }
    }
  }
`;

function App() {
    const [books, setBooks] = useState([]);
    const [showMessage, setShowMessage] = useState(false);
    const [message, setMessage] = useState("");
    const [showAddModal, setShowAddModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);
    const [newTitle, setNewTitle] = useState("");
    const [newIsbn, setNewIsbn] = useState("");
    const [currentIsbn, setCurrentIsbn] = useState("");
    const [currentTitle, setCurrentTitle] = useState("");
    const [sortTitleAsc, setSortTitleAsc] = useState(true);
    const [sortIsbnAsc, setSortIsbnAsc] = useState(true);
    const [sortByIsbn, setSortByIsbn] = useState(true);
    const [sortByTitle, setSortByTitle] = useState(false);


    useEffect(() => {
        axios.get("http://localhost:8080/api/books").then((response) => {
            setBooks(response.data);
        })
    }, [message])

    const sortBooksByTitleAsc = () => {
        axios.get("http://localhost:8080/api/books/sort-title-asc").then((response) => {
            setBooks(response.data);
        })
    }
    const sortBooksByTitleDesc = () => {
        axios.get("http://localhost:8080/api/books/sort-title-desc").then((response) => {
            setBooks(response.data);
        })
    }

    const sortBooksByIsbnAsc = () => {
        axios.get("http://localhost:8080/api/books/sort-isbn-asc").then((response) => {
            setBooks(response.data);
        })
    }
    const sortBooksByIsbnDesc = () => {
        axios.get("http://localhost:8080/api/books/sort-isbn-desc").then((response) => {
            setBooks(response.data);
        })
    }

    const onChangeTitle = (e) => {
        setNewTitle(e.target.value);
    }

    const onChangeIsbn = (e) => {
        setNewIsbn(e.target.value);
    }

    const changeTitle = (isbn, title) => {
        setShowEditModal(false);
        axios.put("http://localhost:8080/api/books/" + isbn + "?title=" + title).then(
            (response) => {
                setShowMessage(true);
                setMessage("Book title changed to " + response.data.title + "!");
                setTimeout(() => {
                    setShowMessage(false);
                }, 3000);
                return Promise.resolve();
            },
            (error) => {
                setShowMessage(true);
                setMessage(error.response.data);
                setTimeout(() => {
                    setShowMessage(false);
                }, 3000);
                return Promise.reject();

            })

    }

    const deleteBook = (isbn) => {
        axios.delete("http://localhost:8080/api/books/" + isbn).then(
            (response) => {
                setShowMessage(true);
                setMessage(response.data);
                setTimeout(() => {
                    setShowMessage(false);
                }, 3000);
                return Promise.resolve();
            },
            (error) => {
                setShowMessage(true);
                setMessage(error.response.data);
                setTimeout(() => {
                    setShowMessage(false);
                }, 3000);
                return Promise.reject();
            })
    }

    const addBook = (isbn, title) => {
        const data = {
            isbn: isbn,
            title: title,
        }
        axios.post("http://localhost:8080/api/books", data).then(
            (response) => {
                setShowAddModal(false);
                setShowMessage(true);
                setMessage("Book with ISBN = " + response.data.isbn + " and title = " + response.data.title + " added successful!");
                setTimeout(() => {
                    setShowMessage(false);
                }, 3000);
                return Promise.resolve();
            },
            (error) => {
                setShowAddModal(false);
                setShowMessage(true);
                setMessage(error.response.data);
                setTimeout(() => {
                    setShowMessage(false);
                }, 3000);
                return Promise.reject();
            })
    }

    const handleSortIsbn = () => {
        setSortByTitle(false);
        setSortByIsbn(true);
        setSortIsbnAsc(!sortIsbnAsc);
    }

    const handleSortTitle = () => {
        setSortByIsbn(false);
        setSortByTitle(true);
        setSortTitleAsc(!sortTitleAsc);
    }

    useEffect(() => {
        sortIsbnAsc ? sortBooksByIsbnAsc() : sortBooksByIsbnDesc();
    }, [sortIsbnAsc]);

    useEffect(() => {
        sortTitleAsc ? sortBooksByTitleAsc() : sortBooksByTitleDesc();
    }, [sortTitleAsc]);

    return (
        <Styles>
            <header><h1>Books in database</h1></header>
            <Modal show={showEditModal} onHide={() => {
                setShowEditModal(false)
            }}>
                <Modal.Header closeButton>
                    <Modal.Title>Change title - {currentTitle}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <input id="change-title-input" type="text" value={newTitle}
                           onChange={onChangeTitle}/>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => {
                        setShowEditModal(false)
                    }}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={() => {
                        changeTitle(currentIsbn, newTitle)
                    }}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showAddModal} onHide={() => {
                setShowAddModal(false)
            }}>
                <Modal.Header closeButton>
                    <Modal.Title>Add new book</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <input id="change-title-input" type="text" value={newIsbn}
                           onChange={onChangeIsbn} placeholder="X-XXXX-XXXXX"/>
                    <input id="change-title-input" type="text" value={newTitle}
                           onChange={onChangeTitle} placeholder="Title"/>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => {
                        setShowAddModal(false)
                    }}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={() => {
                        addBook(newIsbn, newTitle)
                    }}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
            <div id="alert-div">
                <Alert variant="success" show={showMessage}>{message}</Alert>
            </div>
            <div id="table-wrapper">
                <Table striped bordered>
                    <Button id="add-button" variant="success" onClick={() => {
                        setShowAddModal(true)
                    }}>Add</Button>
                    <thead>
                    <tr>
                        <th className={sortIsbnAsc}>
                            <div className="td-wrapper">
                                <button className="sort-button" id={!sortByTitle ? "active" : ""}
                                        onClick={handleSortIsbn}>ISBN-10 {sortIsbnAsc ? <MdArrowDropDown/> : <MdArrowDropUp/>}
                                </button>
                            </div>
                        </th>
                        <th>
                            <div className="td-wrapper">
                                <button className="sort-button" id={!sortByIsbn ? "active" : ""}
                                        onClick={handleSortTitle}>Title {sortTitleAsc ? <MdArrowDropDown/> : <MdArrowDropUp/>}
                                </button>
                            </div>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {books.map((book) => (
                        <tr>
                            <td>
                                <div className="td-wrapper">{book.isbn}</div>
                            </td>
                            <td>
                                <div className="td-wrapper">{book.title}</div>
                            </td>
                            <td id="change-column">
                                <div className="td-wrapper"><Button variant="info" onClick={() => {
                                    setShowEditModal(true);
                                    setCurrentTitle(book.title);
                                    setNewTitle(book.title);
                                    setCurrentIsbn(book.isbn);
                                }}>Change title</Button></div>
                            </td>
                            <td id="delete-column">
                                <div className="td-wrapper"><Button variant="danger" onClick={() => {
                                    deleteBook(book.isbn)
                                }}>X</Button></div>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </div>
        </Styles>
    );
}

export default App;
