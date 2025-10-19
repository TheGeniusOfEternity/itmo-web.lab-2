const form = document.getElementById("form")
const yInput = document.getElementById("y")
const rInput = document.getElementById("r")

const xErrorText = document.getElementById("x-error")
const yErrorText = document.getElementById("y-error")
const rErrorText = document.getElementById("r-error")

form.addEventListener("submit", () => {

  const xInput = document.querySelector('input[name="x-input"]:checked');
  const x = xInput.value
  const y = yInput.value
  const r = rInput.value

  const errorText = validate(x, y, r)

  switch (errorText.input) {
    case "x":
      xErrorText.innerHTML = errorText.text
      break
    case "y":
      yErrorText.innerHTML = errorText.text
      break
    case "r":
      rErrorText.innerHTML = errorText.text
      break
    default:
      console.log(`/main?x=${x}&y=${y}&r=${r}`)
      window.open(`/main?x=${x}&y=${y}&r=${r}`)
  }
})

const validate = (x, y, r) => {
  if (!x || isNaN(parseInt(x)))
    return {
      input: "x",
      text: "Параметр х не является числом"
    }
  if (!y || isNaN(parseInt(y)))
    return {
      input: "y",
      text: "Параметр y не является числом"
    }
  if (!r || isNaN(parseInt(r)))
    return {
      input: "r",
      text: "Параметр r не является числом"
    }
  return {
    input: "",
    text: ""
  }
}