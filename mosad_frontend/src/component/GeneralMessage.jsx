import Alert from '@mui/material/Alert';

const GeneralMessage = ({ message }) => {
  return (
    <Alert variant="filled" severity="success">
        This is a filled success Alert.
      </Alert>
  );
};

export default GeneralMessage;