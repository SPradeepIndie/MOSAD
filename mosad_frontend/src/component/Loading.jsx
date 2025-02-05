import { Container, Typography, CircularProgress } from '@mui/material';
import PropTypes from 'prop-types';


export default function Loading({WhatsLoading}) {
    return (
        <Container maxWidth="lg" sx={{ marginTop: 4, textAlign: 'center' }}>
        <Typography variant="h5" gutterBottom color='primary'>
          Loading {WhatsLoading}...
        </Typography>
      <CircularProgress />
      </Container>
    );
}


Loading.propTypes = {
  WhatsLoading: PropTypes.string.isRequired
};